package com.matiasjuarez.data;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.model.customer.Country;
import com.matiasjuarez.model.money.Currency;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class FakeDataLoaderTest {
    private FakeDataLoader fakeDataLoader;

    @Before
    public void setup() {
        fakeDataLoader = new FakeDataLoader();
    }

    @Test
    public void loadDataFromJsons_retrieveDataFromDB() throws SQLException {
        fakeDataLoader.loadData();

        Dao countryDao = InMemoryDBManager.getInstance().getDaoForClass(Country.class);
        List<Country> countries = countryDao.queryForAll();
        Assert.assertTrue(countries.size() > 0);

        Dao currencyDao = InMemoryDBManager.getInstance().getDaoForClass(Currency.class);
        List<Currency> currencies = currencyDao.queryForAll();
        Assert.assertTrue(currencies.size() > 0);
    }
}
