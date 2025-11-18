package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.client.SoapCountryClient;
import org.acme.mapper.CountryMapper;
import org.acme.model.entity.Country;
import org.acme.client.RestCountryClient;
import org.acme.model.entity.Currency;
import org.acme.model.rest.CountryFromRest;
import org.acme.model.rest.CurrencyFromRest;
import org.acme.model.rest.Name;
import org.acme.repository.CountryRepository;
import org.acme.soapclient.ArrayOftCountryCodeAndName;
import org.acme.soapclient.TCountryCodeAndName;
import org.acme.soapclient.TCountryInfo;
import org.acme.soapclient.TCurrency;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mapstruct.factory.Mappers;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class CountryService {

    private static final String COUNTRIES_QUERY_PARAMS = "name,currencies,cca2";
    private final CountryRepository countryRepository;
    private final SoapCountryClient soapCountryClient;
    private final CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);
    @RestClient
    RestCountryClient restCountryClient;

    CountryService(CountryRepository countryRepository, SoapCountryClient soapCountryClient) {
        this.countryRepository = countryRepository;
        this.soapCountryClient = soapCountryClient;
    }

    public Set<Currency> getCurrenciesFromMap(CountryFromRest countryFromRest, Map<String, Currency> currencies) {
        return currencies.keySet().stream()
                .filter(countryFromRest.getCurrencies().keySet()::contains)
                .map(key -> Optional.ofNullable(currencies.get(key)))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    public void initCountries(List<CountryFromRest> countries, Map<String, Currency> currencies) {
        countries.stream()
                .filter(country -> getCountryEntity(country.getCountryCode()) == null)
                .map(country -> countryMapper.restCountryToCountry(country,currencies,this))
                .forEach(countryRepository::persist);
    }

    public List<CountryFromRest> getCountriesFromRest() { return restCountryClient.getCountryInfo(COUNTRIES_QUERY_PARAMS).stream().toList(); }

    public Country getCountryEntity(String countryCode) { return countryRepository.getCountry(countryCode); }


    //Fetch ALL countries from SOAP and convert into List<CountryFromRest>.
    public List<CountryFromRest> getCountriesFromSoap() {

        List<CountryFromRest> result = new ArrayList<>();
        // Step 1: Get all country codes & names
        ArrayOftCountryCodeAndName array = soapCountryClient.getPort().listOfCountryNamesByCode();
        if (array == null || array.getTCountryCodeAndName() == null) {
            return result;
        }
        for (TCountryCodeAndName entry : array.getTCountryCodeAndName()) {
            try {
                // ISO code
                String iso = entry.getSISOCode();
                if (iso == null || iso.isBlank())
                    continue;
                // Step 2: Get full country info
                TCountryInfo info = soapCountryClient.getPort().fullCountryInfo(iso);
                if (info != null) {
                    // Step 3: Convert to CountryFromRest
                    result.add(countryMapper.soapCountrytoCountryFromRest(info,this));
                }
            } catch (Exception ignored) {
                // Some countries in SOAP API are broken — we skip them safely.
            }
        }
        return result;
    }
    //Context methods for mapping SOAP countries -> REST countries.
    public Name mapName(TCountryInfo soapCountry) {
        return new Name(soapCountry.getSName(), soapCountry.getSName(), Collections.emptyMap());
    }

    public Map<String, CurrencyFromRest> mapCurrencies(TCountryInfo soapCountry) {
        Map<String, CurrencyFromRest> currencies = new HashMap<>();
        String iso = soapCountry.getSCurrencyISOCode();

        if (iso != null && !iso.isBlank()) {
            currencies.put(iso, new CurrencyFromRest("", ""));
        }
        return currencies;
    }
    }
