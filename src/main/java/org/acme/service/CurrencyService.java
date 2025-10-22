package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.mapper.CurrencyMapper;
import org.acme.model.entity.Currency;
import org.acme.model.rest.CountryFromRest;
import org.acme.model.rest.CurrencyFromRest;
import org.acme.repository.CurrencyRepository;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ApplicationScoped
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper = Mappers.getMapper(CurrencyMapper.class);

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Map<String, Currency> initCurrencies(List<CountryFromRest> countries) {
        Currency curr;
        Map<String, Currency> currencies = new HashMap<>();
        for(CountryFromRest countryFromRest : countries) {
            for(Map.Entry<String, CurrencyFromRest> entry: countryFromRest.getCurrencies().entrySet()){
                curr = currencyMapper.restCurrencyToCurrency(entry);
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
