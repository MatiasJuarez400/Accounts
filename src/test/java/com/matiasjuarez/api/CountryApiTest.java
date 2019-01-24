package com.matiasjuarez.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.matiasjuarez.model.customer.Country;
import com.sun.research.ws.wadl.HTTPMethods;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CountryApiTest extends BaseApiTest {

    @Test
    public void executeGetCountry_expectCountryNotNull() throws IOException {
        String bodyContent = executeRequestAndExtractBodyResponse(HTTPMethods.GET, "/countries/GB");

        Country country = mapBodyContentToObject(new TypeReference<Country>() {}, bodyContent);

        assertNotNull(country);
        assertNotNull(country.getCode());
        assertNotNull(country.getName());
    }

    @Test
    public void executeGetCountries_expectListNotEmpty() {
        String bodyContent = executeRequestAndExtractBodyResponse(HTTPMethods.GET,"/countries");

        List<Country> countryList = mapBodyContentToObject(new TypeReference<List<Country>>() {}, bodyContent);

        assertNotNull(countryList);
        assertTrue(countryList.size() > 0);
    }
}
