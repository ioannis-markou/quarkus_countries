package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.Country;
import org.acme.Currency;
import org.acme.model.dto.CountryDTO;
import org.acme.model.dto.CurrencyDTO;

@ApplicationScoped
public class CountryService {

    public Country countryDTOToCountry(CountryDTO countryDTO) {
        Country country = new Country();
        country.setCountryCode(countryDTO.getCountryCode());
        country.setCommonName(countryDTO.getName().getCommon());
        country.setOfficialName(countryDTO.getName().getOfficial());

        return country;
    }

    public Currency currencyDTOToCurrency(CurrencyDTO currencyDTO) {
        Currency currency = new Currency();
        currency.setName(currencyDTO.getName());
        currency.setSymbol(currencyDTO.getSymbol());

        return currency;
    }
}
