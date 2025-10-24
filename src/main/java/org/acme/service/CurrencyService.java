package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.mapper.CurrencyMapper;
import org.acme.model.entity.Currency;
import org.acme.model.rest.CountryFromRest;
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
        Map<String, Currency> currencies = new HashMap<>();

        countries.stream()
                .flatMap(country -> country.getCurrencies().entrySet().stream())
                .map(entry -> Map.entry(entry.getKey(), currencyMapper.restCurrencyToCurrency(entry)))
                .filter(entry -> getCurrencyEntity(entry.getValue().getCurrencyCode()) == null)
                .forEach(entry -> {
                    currencyRepository.persist(entry.getValue());
                    currencies.put(entry.getKey(), entry.getValue());
                });

        return currencies;

    }

    public Currency getCurrencyEntity(String currencyCode) {
        return currencyRepository.getCurrency(currencyCode);
    }
}
