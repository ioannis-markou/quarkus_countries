package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.entity.Currency;
import org.acme.model.rest.CountryFromRest;
import org.acme.model.rest.CurrencyFromRest;
import org.acme.repository.CurrencyRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ApplicationScoped
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Currency currencyDTOToCurrencyEntity(Map.Entry<String, CurrencyFromRest> entry) {
        Currency curr= new Currency();
        curr.setName(entry.getValue().getName());
        curr.setSymbol(entry.getValue().getSymbol());
        curr.setCurrencyCode(entry.getKey());

        return curr;
    }

    public Map<String, Currency> initCurrencies(List<CountryFromRest> countries) {
        Currency curr;
        Map<String, Currency> currencies = new HashMap<>();
        for(CountryFromRest countryFromRest : countries) {
            for(Map.Entry<String, CurrencyFromRest> entry: countryFromRest.getCurrencies().entrySet()){
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
