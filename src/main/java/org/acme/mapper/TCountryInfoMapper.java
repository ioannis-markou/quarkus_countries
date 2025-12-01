package org.acme.mapper;

import org.acme.model.rest.Name;
import org.acme.soapclient.TCountryInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.JAKARTA_CDI;


@Mapper(
        componentModel = JAKARTA_CDI,
        injectionStrategy = CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        imports = {Collections.class}
)
public interface TCountryInfoMapper {

    //Context methods for mapping SOAP countries -> REST countries.
    @Mapping(target = "common", source = "SName")
    @Mapping(target = "official", source = "SName")
    @Mapping(target = "nativeNames", expression = "java(Collections.emptyMap())")
    Name mapName(TCountryInfo soapCountry);
}
