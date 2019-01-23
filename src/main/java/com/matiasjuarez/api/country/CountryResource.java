package com.matiasjuarez.api.country;

import com.matiasjuarez.api.ApiUtils;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.model.customer.Country;
import com.matiasjuarez.utils.JsonConverter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/countries")
@Produces(MediaType.APPLICATION_JSON)
public class CountryResource {

    @Inject
    private CountryService countryService;

    public CountryResource(CountryService countryService) {
        this.countryService = countryService;
    }

    public CountryResource() {}

    @GET
    @Path("/{countryCode}")
    public Response getCountry(@PathParam("countryCode") String countryCode) throws SQLException {
        Country retrievedCountry = countryService.getCountry(countryCode);

        if (retrievedCountry == null) {
            throw new EntityNotFoundException(EntityNames.COUNTRY, countryCode);
        }

        return ApiUtils.buildOkResponse(retrievedCountry);
    }

    @GET
    public Response getCountries() throws SQLException {
        List<Country> countries = countryService.getCountries();

        return ApiUtils.buildOkResponse(countries);
    }
}
