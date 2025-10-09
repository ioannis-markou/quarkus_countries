package org.acme.init;

import io.quarkus.runtime.Startup;
import jakarta.transaction.Transactional;
import org.acme.service.CountryService;
import org.acme.service.CurrencyService;

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
        countryService.initCountries();
        currencyService.initCurrencies();
    }
}
