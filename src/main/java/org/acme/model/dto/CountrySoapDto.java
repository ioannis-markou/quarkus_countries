package org.acme.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlRootElement(name = "country")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"commonName", "code", "officialName", "currencies"})
public class CountrySoapDto {

    private String commonName;
    private String code;
    private String officialName;
    private List<String> currencies;

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }
}
