package org.acme;

import java.util.List;

public class Output {
    private String commonName;
    private String countryCode;
    private String officialName;
    private List<String> currenciesCode;

    public Output(String commonName, String countryCode, String officialName, List<String> currenciesCode) {
        this.commonName = commonName;
        this.countryCode = countryCode;
        this.officialName = officialName;
        this.currenciesCode = currenciesCode;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public List<String> getCurrenciesCode() {
        return currenciesCode;
    }

    public void setCurrenciesCode(List<String> currenciesCode) {
        this.currenciesCode = currenciesCode;
    }
}
