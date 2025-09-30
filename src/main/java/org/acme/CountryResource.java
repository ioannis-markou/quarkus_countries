package org.acme;

import io.quarkus.runtime.Startup;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.dto.CountryDTO;
import org.acme.model.dto.CurrencyDTO;
import org.acme.repository.CountryRepository;
import org.acme.repository.CurrencyRepository;
import org.acme.service.CountryService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Set;


@Path("/country")
public class CountryResource {

    @Inject
    CountryRepository countryRepository;
    @RestClient
    CountryClient countriesService;

    private static final String COUNTRIES_QUERY_PARAMS = "name,currencies,cca2";
    @Inject
    CurrencyRepository currencyRepository;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<CountryDTO> countryInfo() {
        return countriesService.getCountryInfo(COUNTRIES_QUERY_PARAMS);
    }

    @GET
    @Path("/currency/{currencyCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Country> countryCurrency(String currencyCode) {
        return countryRepository.list("countryCode", "GR");
    }

    @Startup
    @Transactional
     void populateTables() {
        var countries = countriesService.getCountryInfo(COUNTRIES_QUERY_PARAMS).stream().toList();
        CountryService countryService = new CountryService();
        for(CountryDTO countryDTO : countries) {
            Country country = countryService.countryDTOToCountry(countryDTO);
            countryRepository.persist(country);

            for(CurrencyDTO currencyDTO: countryDTO.getCurrencies().values()) {
                Currency currency = countryService.currencyDTOToCurrency(currencyDTO);
                if(currencyRepository.find("name", currency.getName()).firstResult() == null) {
                    currencyRepository.persist(currency);
                }
            }
        }
    }
}
