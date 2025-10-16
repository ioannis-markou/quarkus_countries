package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Currency;
import org.acme.model.dto.CountryDTO;
import org.acme.model.dto.CurrencyDTO;
import org.acme.repository.CurrencyRepository;

import java.util.HashMap;
import java.util.Map;
@ApplicationScoped
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CountryService countryService;

    public CurrencyService(CurrencyRepository currencyRepository, CountryService countryService) {
        this.currencyRepository = currencyRepository;
        this.countryService = countryService;
    }

    public Currency currencyDTOToCurrencyEntity(Map.Entry<String, CurrencyDTO> entry) {
        Currency curr= new Currency();
        curr.setName(entry.getValue().getName());
        curr.setSymbol(entry.getValue().getSymbol());
        curr.setCurrencyCode(entry.getKey());

        return curr;
    }

    public Map<String, Currency> initCurrencies() {
        var countries = countryService.getCountries();
        Currency curr;
        Map<String, Currency> currencies = new HashMap<>();
        for(CountryDTO countryDTO : countries) {
            for(Map.Entry<String, CurrencyDTO> entry: countryDTO.getCurrencies().entrySet()){
                curr = currencyDTOToCurrencyEntity(entry);
                if(getCurrencyEntity(curr.getCurrencyCode()) == null) {
                    currencyRepository.persist(curr);
                    currencies.put(entry.getKey(), curr);
                }
            }
        }
        return currencies;
    }

    public Currency getCurrencyEntity(String currencyCode) {
        return currencyRepository.getCurrency(currencyCode);
    }
}
