package org.acme.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.rest.CountryFromRest;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Set;

@RegisterRestClient(configKey = "countries")
public interface CountryClient {

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    Set<CountryFromRest> getCountryInfo(@QueryParam("fields") String queryParams);
}
