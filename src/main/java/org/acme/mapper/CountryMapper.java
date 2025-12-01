package org.acme.mapper;

import org.acme.model.dto.CountrySoapDto;
import org.acme.model.entity.Country;
import org.acme.model.entity.Currency;
import org.acme.model.rest.CountryFromRest;
import org.acme.service.CountryService;
import org.acme.soapclient.TCountryInfo;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.Map;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.JAKARTA_CDI;

@Mapper(
        componentModel = JAKARTA_CDI,
        injectionStrategy = CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = { TCountryInfoMapper.class },
        imports = {Collections.class}
)
public interface CountryMapper {

    @Mapping(target = "officialName", expression = "java(countryFromRest.getName().getOfficial())")
    @Mapping(target = "commonName", expression = "java(countryFromRest.getName().getCommon())")
    @Mapping(target = "currencies", expression = "java(context.getCurrenciesFromMap(countryFromRest,currencies))")
    @Mapping(target = "countryCode", source = "countryFromRest.countryCode")
    @Mapping(target = "id", ignore = true)
    Country restCountryToCountry(CountryFromRest countryFromRest, Map<String, Currency> currencies, @Context CountryService context);

    @Mapping(target = "name", source = "soapCountry")
    @Mapping(target = "countryCode", source = "SISOCode")
    @Mapping(target = "currencies", expression = "java(context.mapCurrencies(soapCountry))")
    CountryFromRest soapCountrytoCountryFromRest(TCountryInfo soapCountry, @Context CountryService context);

    @Mapping(target = "code", source = "countryCode")
    @Mapping(target = "currencies", expression = "java(country.getCurrencies().stream().map(Currency::getCurrencyCode).toList())")
    CountrySoapDto entityToSoap(Country country);
}
