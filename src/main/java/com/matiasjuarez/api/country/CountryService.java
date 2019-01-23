package com.matiasjuarez.api.country;

import com.matiasjuarez.model.customer.Country;

import java.sql.SQLException;
import java.util.List;

public interface CountryService {
    Country getCountry(String countryCode) throws SQLException;
    List<Country> getCountries() throws SQLException;
}
