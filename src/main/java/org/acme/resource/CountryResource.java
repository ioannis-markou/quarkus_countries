package org.acme.resource;


import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.dto.CountryDto;
import org.acme.repository.CountryRepository;
import org.acme.repository.CurrencyRepository;

import java.util.List;


@Path("/countries")
public class CountryResource {
    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;

    CountryResource(CountryRepository countryRepository, CurrencyRepository currencyRepository) {
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
    }

    @GET
    @Path("/code/{countryCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public CountryDto findByCountryCode(@PathParam("countryCode") String countryCode) {
        return countryRepository
                .getCountry(countryCode)
                .toOutput();
    }

    @GET
    @Path("/currency/{currencyCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<CountryDto> findByCurrencyCode(@PathParam("currencyCode") String currencyCode) {
        return currencyRepository.getCurrency(currencyCode).toOutputList();
    }
}
