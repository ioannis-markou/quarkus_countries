package org.acme.init;

import io.quarkus.runtime.Startup;
import jakarta.transaction.Transactional;
import org.acme.entity.Currency;
import org.acme.service.CountryService;
import org.acme.service.CurrencyService;

import java.util.Map;

@Startup
public class ApplicationInitializer {

    private final CountryService countryService;
    private final CurrencyService currencyService;

    public ApplicationInitializer(CountryService countryService, CurrencyService currencyService) {
        this.countryService = countryService;
        this.currencyService = currencyService;
    }

    @Transactional
    @Startup
    void populateCountriesTable() {
        Map<String, Currency> currencies = currencyService.initCurrencies();
        countryService.initCountries(currencies);
    }
}
