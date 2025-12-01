package org.acme.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.dto.CountryDto;
import org.acme.soapclient.CountryInfoServiceSoapType;

import java.util.List;

//TODO This Client has been made redundant. The appropriate interface and concrete implementation are generated through the application properties
@ApplicationScoped
public class SoapCountryClient {


    private final CountryInfoServiceSoapType countrySoapService;

    @Inject
    public SoapCountryClient(CountryInfoServiceSoapType countrySoapService)
    {
        this.countrySoapService = countrySoapService;
    }

    public CountryDto getCountry(String iso) {
        var soapCountry= countrySoapService.fullCountryInfo(iso);
        return new CountryDto(soapCountry.getSName(), soapCountry.getSISOCode(), soapCountry.getSName(), List.of(soapCountry.getSCurrencyISOCode()));
    }

}
