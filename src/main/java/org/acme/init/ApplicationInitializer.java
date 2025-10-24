package org.acme.init;

import io.quarkus.runtime.Startup;
import jakarta.transaction.Transactional;
import org.acme.model.entity.Currency;
import org.acme.service.CountryService;
import org.acme.service.CurrencyService;

import java.util.Map;

public class ApplicationInitializer {

    private final CountryService countryService;
    private final CurrencyService currencyService;

    public ApplicationInitializer(CountryService countryService, CurrencyService currencyService) {
        this.countryService = countryService;
        this.currencyService = currencyService;
    }

    @Startup
    @Transactional
    void populateTables() {
        var countries = countryService.getCountries();
        Map<String, Currency> currencies = currencyService.initCurrencies(countries);
        countryService.initCountries(countries, currencies);
    }
}
