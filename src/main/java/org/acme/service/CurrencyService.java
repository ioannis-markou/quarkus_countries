package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.Currency;
import org.acme.model.dto.CountryDTO;
import org.acme.model.dto.CurrencyDTO;
import org.acme.repository.CurrencyRepository;

import java.util.Map;
@ApplicationScoped
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CountryService countryService;

    public CurrencyService(CurrencyRepository currencyRepository, CountryService countryService) {
        this.currencyRepository = currencyRepository;
        this.countryService = countryService;
    }

    public Currency currencyDTOToCurrency(Map.Entry<String, CurrencyDTO> entry) {
        Currency curr= new Currency();
        curr.setName(entry.getValue().getName());
        curr.setSymbol(entry.getValue().getSymbol());
        curr.setCurrencyCode(entry.getKey());

        return curr;
    }

    public void initCurrencies() {
        var countries = countryService.getCountries();
        Currency curr;
        for(CountryDTO countryDTO : countries) {
            for(Map.Entry<String, CurrencyDTO> entry: countryDTO.getCurrencies().entrySet()){
                curr = currencyDTOToCurrency(entry);
                if(getCurrencyEntity(curr.getCurrencyCode()) == null) {
                    currencyRepository.persist(curr);
                    countryService.getCountryEntity(countryDTO.getCountryCode()).getCurrencies().add(curr);
                }
            }
        }
    }

    public Currency getCurrencyEntity(String currencyCode) {
        return currencyRepository.findByCode(currencyCode);
    }
}
