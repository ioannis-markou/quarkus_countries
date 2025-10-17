package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.entity.Country;
import org.acme.client.CountryClient;
import org.acme.model.entity.Currency;
import org.acme.model.rest.CountryFromRest;
import org.acme.repository.CountryRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class CountryService {

    private static final String COUNTRIES_QUERY_PARAMS = "name,currencies,cca2";
    private final CountryRepository countryRepository;
    @RestClient
    CountryClient countryClient;

    CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country countryDTOToCountry(CountryFromRest countryFromRest, Map<String, Currency> currencies) {
        Country country = new Country();
        country.setCountryCode(countryFromRest.getCountryCode());
        country.setCommonName(countryFromRest.getName().getCommon());
        country.setOfficialName(countryFromRest.getName().getOfficial());

        Set<Currency> currenciesSet = new HashSet<>();
        Set<String> currenciesNames = countryFromRest.getCurrencies().keySet();
        for(Map.Entry<String, Currency> entry : currencies.entrySet()) {
            if(currenciesNames.contains(entry.getKey())) {
                currenciesSet.add(entry.getValue());
            }
        }

        country.setCurrencies(currenciesSet);

        return country;
    }

    public void initCountries(List<CountryFromRest> countries, Map<String, Currency> currencies) {
        for(CountryFromRest countryFromRest : countries) {
            if(getCountryEntity(countryFromRest.getCountryCode()) == null) {
                countryRepository.persist(countryDTOToCountry(countryFromRest,currencies));
            }
        }
    }

    public List<CountryFromRest> getCountries() {
        return countryClient.getCountryInfo(COUNTRIES_QUERY_PARAMS).stream().toList();
    }

    public Country getCountryEntity(String countryCode) {
        return countryRepository.getCountry(countryCode);
    }

}
