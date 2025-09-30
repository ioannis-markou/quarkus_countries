package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.Currency;

@ApplicationScoped
public class CurrencyRepository implements PanacheRepository<Currency> {
}
