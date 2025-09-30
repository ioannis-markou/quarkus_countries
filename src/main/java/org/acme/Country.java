package org.acme;

import jakarta.persistence.*;

@Entity
@Table(name="countries")
public class Country{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String countryCode;
    private String officialName;
    private String commonName;

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


}
