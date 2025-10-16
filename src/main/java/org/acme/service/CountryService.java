package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Country;
import org.acme.client.CountryClient;
import org.acme.entity.Currency;
import org.acme.model.dto.CountryDTO;
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

    public Country countryDTOToCountry(CountryDTO countryDTO, Map<String, Currency> currencies) {
        Country country = new Country();
        country.setCountryCode(countryDTO.getCountryCode());
        country.setCommonName(countryDTO.getName().getCommon());
        country.setOfficialName(countryDTO.getName().getOfficial());

        Set<Currency> currenciesSet = new HashSet<>();
        Set<String> currenciesNames = countryDTO.getCurrencies().keySet();
        for(Map.Entry<String, Currency> entry : currencies.entrySet()) {
            if(currenciesNames.contains(entry.getKey())) {
                currenciesSet.add(entry.getValue());
            }
        }

        country.setCurrencies(currenciesSet);

        return country;
    }

    public void initCountries(Map<String, Currency> currencies) {
        var countries = getCountries();
        for(CountryDTO countryDTO : countries) {
            if(getCountryEntity(countryDTO.getCountryCode()) == null) {
                countryRepository.persist(countryDTOToCountry(countryDTO,currencies));
            }
        }
    }

    public List<CountryDTO> getCountries() {
        return countryClient.getCountryInfo(COUNTRIES_QUERY_PARAMS).stream().toList();
    }

    public Country getCountryEntity(String countryCode) {
        return countryRepository.getCountry(countryCode);
    }

}
