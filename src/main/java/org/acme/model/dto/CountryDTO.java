package org.acme.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class CountryDTO {
    @JsonProperty("name")
    private Name name;
    @JsonProperty("cca2")
    private String countryCode;
    private Map<String, CurrencyDTO> currencies;

    public CountryDTO(Name name, String countryCode, Map<String, CurrencyDTO> currencies) {
        this.name = name;
        this.countryCode = countryCode;
        this.currencies = currencies;
    }

    public Map<String, CurrencyDTO> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, CurrencyDTO> currencies) {
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
