package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Currency;

@ApplicationScoped
public class CurrencyRepository implements PanacheRepository<Currency> {

    public Currency getCurrency(String code) {
        return find("currencyCode", code).firstResult();
    }
}
