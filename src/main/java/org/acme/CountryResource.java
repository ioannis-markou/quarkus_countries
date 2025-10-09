package org.acme;


import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.acme.repository.CountryRepository;
import org.acme.repository.CurrencyRepository;



@Path("/country")
public class CountryResource {
    private final CurrencyRepository currencyRepository;

    CountryResource(CurrencyRepository currencyRepository, CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
    }

    //Not correct, to be implemented.
    @GET
    @Path("/currency/{currencyCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Currency countryCurrency(String currencyCode) {
        return currencyRepository.findByCode(currencyCode);
    }
}
