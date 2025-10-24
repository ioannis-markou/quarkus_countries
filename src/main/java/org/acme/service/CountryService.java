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

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class CountryService {

    private static final String COUNTRIES_QUERY_PARAMS = "name,currencies,cca2";
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);
    @RestClient
    CountryClient countryClient;

    CountryService(CountryRepository countryRepository) { this.countryRepository = countryRepository; }

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

    public List<CountryFromRest> getCountries() { return countryClient.getCountryInfo(COUNTRIES_QUERY_PARAMS).stream().toList(); }

    public Country getCountryEntity(String countryCode) { return countryRepository.getCountry(countryCode); }
}
