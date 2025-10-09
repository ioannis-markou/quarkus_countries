package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.Currency;

import java.util.List;

@ApplicationScoped
public class CurrencyRepository implements PanacheRepository<Currency> {

    public Currency findByCode(String code) {
        return find("currencyCode", code).firstResult();
    }

    public List<Currency> findCurrencies(int id) {
        return list("id", id);
    }
}
