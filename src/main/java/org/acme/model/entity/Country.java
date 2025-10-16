package org.acme.model.entity;

import jakarta.persistence.*;
import org.acme.model.dto.CountryDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="country_code")
    private String countryCode;
    @Column(name="official_name")
    private String officialName;
    @Column(name="common_name")
    private String commonName;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="country_currency", joinColumns = @JoinColumn(name = "country_id") , inverseJoinColumns = @JoinColumn(name = "currency_id"))
    private Set<Currency> currenciesSet= new HashSet<>() ;

    public Country() {
        //No-arg constructor for JPA entity.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public Set<Currency> getCurrencies() {
        return currenciesSet;
    }

    public void setCurrencies(Set<Currency> currencies) {
        this.currenciesSet = currencies;
    }

    //This should be a mapstruct mapper
    public CountryDto toOutput() {
        List<String> currencies = new ArrayList<>();
        for(Currency currency : this.getCurrencies()) {
            currencies.add(currency.getCurrencyCode());
        }
        return new CountryDto(this.getCommonName(), this.getCountryCode(), this.getOfficialName(),currencies);
    }
}
