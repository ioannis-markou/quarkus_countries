package org.acme.resource;


import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.dto.CountryDto;
import org.acme.model.rest.CountryFromRest;
import org.acme.repository.CountryRepository;
import org.acme.repository.CurrencyRepository;
import org.acme.client.SoapCountryClient;
import org.acme.model.dto.CountryDto;
import org.acme.soapclient.TCountryCodeAndName;
import org.acme.soapclient.TCountryInfo;

import java.util.List;


@Path("/countries")
public class CountryResource {
    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;
    private final SoapCountryClient soapCountryClient;

    CountryResource(CountryRepository countryRepository, CurrencyRepository currencyRepository, SoapCountryClient soapCountryClient) {
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.soapCountryClient = soapCountryClient;
    }

    @GET
    @Path("/code/{countryCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public CountryDto findByCountryCode(@PathParam("countryCode") String countryCode) {
        return countryRepository.getCountry(countryCode).toCountryDto();
    }

    @GET
    @Path("/currency/{currencyCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<CountryDto> findByCurrencyCode(@PathParam("currencyCode") String currencyCode) {
        return currencyRepository.getCurrency(currencyCode).toCountryDtoList();
    }

    @GET
    @Path("/soap/{countryCode}")
    @Produces(MediaType.APPLICATION_JSON) // or MediaType.APPLICATION_XML
    public CountryDto findByCountryCodeSoap(@PathParam("countryCode") String countryCode) {
        // Directly call SOAP client and return the SOAP-generated object
        return soapCountryClient.getCountry(countryCode);
    }
}
