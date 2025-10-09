package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.Country;
import org.acme.CountryClient;
import org.acme.model.dto.CountryDTO;
import org.acme.repository.CountryRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class CountryService {

    private static final String COUNTRIES_QUERY_PARAMS = "name,currencies,cca2";
    private final CountryRepository countryRepository;
    @RestClient
    CountryClient countryClient;

    CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country countryDTOToCountry(CountryDTO countryDTO) {
        Country country = new Country();
        country.setCountryCode(countryDTO.getCountryCode());
        country.setCommonName(countryDTO.getName().getCommon());
        country.setOfficialName(countryDTO.getName().getOfficial());

        return country;
    }

    public void initCountries() {
        var countries = getCountries();
        for(CountryDTO countryDTO : countries) {
            if(getCountryEntity(countryDTO.getCountryCode()) == null)
                countryRepository.persist(countryDTOToCountry(countryDTO));
        }
    }

    public List<CountryDTO> getCountries() {
        return countryClient.getCountryInfo(COUNTRIES_QUERY_PARAMS).stream().toList();
    }

    public Country getCountryEntity(String countryCode) {
        return countryRepository.findByCode(countryCode);
    }
}
