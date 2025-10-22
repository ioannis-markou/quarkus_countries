package org.acme.mapper;

import org.acme.model.entity.Country;
import org.acme.model.entity.Currency;
import org.acme.model.rest.CountryFromRest;
import org.acme.service.CountryService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper
public interface CountryMapper {

    @Mapping(target = "officialName", expression = "java(countryFromRest.getName().getOfficial())")
    @Mapping(target = "commonName", expression = "java(countryFromRest.getName().getCommon())")
    @Mapping(target = "currencies", expression = "java(context.getCurrenciesFromMap(countryFromRest,currencies))")
    @Mapping(target = "countryCode", source = "countryFromRest.countryCode")
    @Mapping(target = "id", ignore = true)
    Country restCountryToCountry(CountryFromRest countryFromRest, Map<String, Currency> currencies, @Context CountryService context);
}
