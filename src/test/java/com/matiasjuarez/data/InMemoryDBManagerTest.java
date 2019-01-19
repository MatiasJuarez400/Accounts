package com.matiasjuarez.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.matiasjuarez.customer.Country;
import com.matiasjuarez.money.Currency;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class InMemoryDBManagerTest {
    private InMemoryDBManager inMemoryDBManager;

    @Before
    public void setup() {
        this.inMemoryDBManager = InMemoryDBManager.getInstance();
    }

    @Test
    public void getDAOsFromManager_expectCreatedDAOsForPersistenceClasses() {
        Class[] persistedClasses = {
                Country.class, Currency.class
        };

        Assert.assertEquals(persistedClasses.length, inMemoryDBManager.getDaos().size());

        for (Class clazz : persistedClasses) {
            Assert.assertNotNull(inMemoryDBManager.getDaoForClass(clazz));
        }
    }

    @Test
    public void insertNewCountry_retrieveCountryFromDB_expectNotNullCountry() throws SQLException {
        Dao<Country, String> dao = inMemoryDBManager.getDaoForClass(Country.class);

        dao.create(new Country("AR", "Argentina"));

        Country argentina = dao.queryForId("AR");

        Assert.assertNotNull(argentina);
        Assert.assertEquals("AR", argentina.getCode());
        Assert.assertEquals("Argentina", argentina.getName());
    }
}
