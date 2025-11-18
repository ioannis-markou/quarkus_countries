package org.acme.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.dto.CountryDto;
import org.acme.soapclient.CountryInfoService;
import org.acme.soapclient.CountryInfoServiceSoapType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.List;

@ApplicationScoped
public class SoapCountryClient {

    private final CountryInfoServiceSoapType port;

    @Inject
    public SoapCountryClient(
            @ConfigProperty(name = "soap.wsdl.url") String wsdlUrl,
            @ConfigProperty(name = "soap.namespace") String namespace,
            @ConfigProperty(name = "soap.service.name") String serviceName,
            @ConfigProperty(name = "soap.port.name") String portName)
    {
        try {
            URL wsdl = new URL(wsdlUrl);

            QName SERVICE_QNAME = new QName(namespace, serviceName);
            QName PORT_QNAME = new QName(namespace, portName);

            CountryInfoService service = new CountryInfoService(wsdl, SERVICE_QNAME);

            this.port = service.getPort(PORT_QNAME, CountryInfoServiceSoapType.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize SOAP client", e);
        }
    }

    public CountryDto getCountry(String iso) {
        var soapCountry= port.fullCountryInfo(iso);
        return new CountryDto(soapCountry.getSName(), soapCountry.getSISOCode(), soapCountry.getSName(), List.of(soapCountry.getSCurrencyISOCode()));
    }

    public CountryInfoServiceSoapType getPort() {
        return port;
    }
}
