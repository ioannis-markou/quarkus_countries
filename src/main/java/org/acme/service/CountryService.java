package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.mapper.CountryMapper;
import org.acme.model.entity.Country;
import org.acme.client.CountryClient;
import org.acme.model.entity.Currency;
import org.acme.model.rest.CountryFromRest;
import org.acme.repository.CountryRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class CountryService {

    private static final String COUNTRIES_QUERY_PARAMS = "name,currencies,cca2";
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);
    @RestClient
    CountryClient countryClient;

    CountryService(CountryRepository countryRepository) { this.countryRepository = countryRepository; }

    public Set<Currency>  getCurrenciesFromMap(CountryFromRest countryFromRest, Map<String, Currency> currencies) {
        Set<Currency> currenciesSet = new HashSet<>();
        Set<String> currenciesNames = countryFromRest.getCurrencies().keySet();
        for(Map.Entry<String, Currency> entry : currencies.entrySet()) {
            if(currenciesNames.contains(entry.getKey())) {
                currenciesSet.add(entry.getValue());
            }
        }
        return currenciesSet;
    }

    public void initCountries(List<CountryFromRest> countries, Map<String, Currency> currencies) {
        for(CountryFromRest countryFromRest : countries) {
            if(getCountryEntity(countryFromRest.getCountryCode()) == null) {
                countryRepository.persist(countryMapper.restCountryToCountry(countryFromRest,currencies,this));
            }
        }
    }

    public List<CountryFromRest> getCountries() { return countryClient.getCountryInfo(COUNTRIES_QUERY_PARAMS).stream().toList(); }

    public Country getCountryEntity(String countryCode) {
        return countryRepository.getCountry(countryCode);
    }
}
