package com.matiasjuarez.api.country;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.customer.Country;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryServiceImpl extends BaseService implements CountryService {

    @Override
    public Country getCountry(String countryCode) throws SQLException {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("code", countryCode);

        List<Country> retrievedCountriesList = getDao().queryForFieldValues(queryParams);

        return retrievedCountriesList.size() > 0 ? retrievedCountriesList.get(0) : null;
    }

    @Override
    public List<Country> getCountries() throws SQLException {
        return getDao().queryForAll();
    }

    private Dao<Country, Long> getDao() {
        return inMemoryDBManager.getDaoForClass(Country.class);
    }
}
