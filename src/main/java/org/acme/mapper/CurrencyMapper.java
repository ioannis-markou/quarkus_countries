package org.acme.mapper;

import org.acme.model.entity.Currency;
import org.acme.model.rest.CurrencyFromRest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Map;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.JAKARTA_CDI;

@Mapper(
        componentModel = JAKARTA_CDI,
        injectionStrategy = CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface CurrencyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", expression = "java(entry.getValue().getName())")
    @Mapping(target = "symbol", expression = "java(entry.getValue().getSymbol())")
    @Mapping(target = "currencyCode", expression = "java(entry.getKey())")
    @Mapping(target = "countries", ignore = true)
    Currency restCurrencyToCurrency(Map.Entry<String, CurrencyFromRest> entry);
}
