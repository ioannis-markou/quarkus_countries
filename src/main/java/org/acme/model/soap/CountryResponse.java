package org.acme.model.soap;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import org.acme.model.dto.CountrySoapDto;

@XmlRootElement(name = "countryResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryResponse {

    private CountrySoapDto country;

    public CountryResponse() {}

    public CountryResponse(CountrySoapDto country) {
        this.country = country;
    }

    public CountrySoapDto getCountry() {
        return country;
    }

    public void setCountry(CountrySoapDto country) {
        this.country = country;
    }
}