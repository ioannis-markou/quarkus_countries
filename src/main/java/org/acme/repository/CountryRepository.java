package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.Country;

@ApplicationScoped
public class CountryRepository implements PanacheRepository<Country> {

    public Country findByCode(String code) {
        return find("countryCode", code).firstResult();
    }
}
