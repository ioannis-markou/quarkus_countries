package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Country;

@ApplicationScoped
public class CountryRepository implements PanacheRepository<Country> {

    public Country getCountry(String code) {
        return find("countryCode", code).firstResult();
    }

}
