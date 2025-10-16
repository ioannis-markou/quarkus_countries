package org.acme.entity;

import jakarta.persistence.*;
import org.acme.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String symbol;
    @Column(name="currency_code")
    private String currencyCode;
    @ManyToMany(mappedBy = "currenciesSet",fetch = FetchType.EAGER)
    private Set<Country> countries ;

    public Currency() {
        //No-arg constructor for JPA entity.
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public List<Output> toOutputList() {
        List<Output> outputs = new ArrayList<>();

        for(Country country : this.getCountries()) {
            outputs.add(country.toOutput());
        }
        return outputs;
    }
}
