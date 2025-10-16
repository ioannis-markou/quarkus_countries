package org.acme.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class CountryFromRest {
    @JsonProperty("name")
    private Name name;
    @JsonProperty("cca2")
    private String countryCode;
    private Map<String, CurrencyFromRest> currencies;

    public CountryFromRest(Name name, String countryCode, Map<String, CurrencyFromRest> currencies) {
        this.name = name;
        this.countryCode = countryCode;
        this.currencies = currencies;
    }

    public Map<String, CurrencyFromRest> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, CurrencyFromRest> currencies) {
        this.currencies = currencies;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
