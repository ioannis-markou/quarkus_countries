package org.acme.model.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.inject.Inject;
import org.acme.model.dto.CountrySoapDto;
import org.acme.model.entity.Country;
import org.acme.repository.CurrencyRepository;
import org.acme.service.CountryService;

import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class SoapCountryService {

    private final CountryService countryService;
    private final CurrencyRepository currencyRepository;

    public SoapCountryService(CountryService countryService, CurrencyRepository currencyRepository) {
        this.countryService = countryService;
        this.currencyRepository = currencyRepository;
    }

    @WebMethod
    public CountryResponse getCountryByCode(@WebParam(name = "countryCode") String countryCode) {
        Country country = countryService.getCountryEntity(countryCode);
        if (country == null) {
            return new CountryResponse(); // empty response or implement a fault
        }
        return countryService.entityToCountryResponse(country);
    }

    @WebMethod
    public CountriesResponse getCountriesByCurrency(@WebParam(name = "currencyCode") String currencyCode) {
        List<Country> countries = currencyRepository.getCurrency(currencyCode).getCountries().stream().toList();
        return countryService.entityListToCountriesResponse(countries);
    }
}