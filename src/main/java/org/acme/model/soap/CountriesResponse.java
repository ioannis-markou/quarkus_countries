package org.acme.model.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import org.acme.model.dto.CountrySoapDto;

import java.util.List;

@XmlRootElement(name = "countriesResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountriesResponse {

    @XmlElement(name = "country")
    private List<CountrySoapDto> countries;

    public CountriesResponse() {}

    public CountriesResponse(List<CountrySoapDto> countries) {
        this.countries = countries;
    }

    public List<CountrySoapDto> getCountries() {
        return countries;
    }

    public void setCountries(List<CountrySoapDto> countries) {
        this.countries = countries;
    }
}