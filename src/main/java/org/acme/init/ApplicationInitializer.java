package org.acme.init;

import io.quarkus.runtime.Startup;
import jakarta.transaction.Transactional;
import org.acme.model.entity.Currency;
import org.acme.service.CountryService;
import org.acme.service.CurrencyService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Map;

public class ApplicationInitializer {

    private final CountryService countryService;
    private final CurrencyService currencyService;
    @ConfigProperty(name = "country-service.data-source")
    String dataSource;
    @ConfigProperty(name = "country-service.sync-on-startup", defaultValue = "true")
    boolean syncOnStartup;


    public ApplicationInitializer(CountryService countryService, CurrencyService currencyService) {
        this.countryService = countryService;
        this.currencyService = currencyService;
    }

    @Startup
    @Transactional
    void populateTables() {

        if (!syncOnStartup) {
            System.out.println("Skipping DB sync on startup.");
            return;
        }

        var countries = switch (dataSource) {
            case "restcountries" -> countryService.getCountriesFromRest();
            case "oorsprong" -> countryService.getCountriesFromSoap();
            default -> throw new IllegalArgumentException("Unknown data source: " + dataSource);
        };

        Map<String, Currency> currencies = currencyService.initCurrencies(countries);
        countryService.initCountries(countries, currencies);
    }
}
