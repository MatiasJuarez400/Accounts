package com.matiasjuarez.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.matiasjuarez.customer.Country;
import com.matiasjuarez.customer.Customer;
import com.matiasjuarez.customer.CustomerAccount;
import com.matiasjuarez.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.monetaryaccount.transaction.Transaction;
import com.matiasjuarez.money.Currency;
import com.matiasjuarez.money.Money;
import org.h2.tools.Server;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

public class InMemoryDBManagerTest {
    private InMemoryDBManager inMemoryDBManager;

    @Before
    public void setup() {
        this.inMemoryDBManager = InMemoryDBManager.getInstance();
    }

    @Test
    public void getDAOsFromManager_expectCreatedDAOsForPersistenceClasses() throws Exception {
        Class[] persistedClasses = {
                Country.class,
                Currency.class,
                Customer.class,
                CustomerAccount.class,
                MonetaryAccount.class
        };

//        Assert.assertEquals(persistedClasses.length, inMemoryDBManager.getDaos().size());

        for (Class clazz : persistedClasses) {
            Assert.assertNotNull(inMemoryDBManager.getDaoForClass(clazz));
        }

        Server webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

        Dao<MonetaryAccount, Long> dao = inMemoryDBManager.getDaoForClass(MonetaryAccount.class);
        MonetaryAccount monetaryAccount = new MonetaryAccount();
//        monetaryAccount.setMoney(new Money(new BigDecimal(1234), "ARS"));
        dao.create(monetaryAccount);

        Dao<Transaction, Long> doa = inMemoryDBManager.getDaoForClass(Transaction.class);
        Transaction trx = new Transaction();
        trx.setTransferedMoney(new Money(new BigDecimal("123456.1566"), "CAD"));
        doa.create(trx);
        Thread.sleep(9999999);
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
