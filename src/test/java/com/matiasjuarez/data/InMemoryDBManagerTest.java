package com.matiasjuarez.data;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.model.customer.Country;
import com.matiasjuarez.model.customer.Customer;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.monetaryaccount.transaction.Transaction;
import com.matiasjuarez.model.money.Currency;
import com.matiasjuarez.model.money.Money;
import org.h2.tools.Server;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;

public class InMemoryDBManagerTest {
    private InMemoryDBManager inMemoryDBManager;

    @Before
    public void setup() {
        this.inMemoryDBManager = InMemoryDBManager.getInstance();
    }

    @Test
    public void getDAOsFromManager_expectCreatedDAOsForPersistenceClasses() {
        Class[] persistedClasses = {
                Country.class,
                Currency.class,
                Customer.class,
                CustomerAccount.class,
                MonetaryAccount.class,
                Transaction.class
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

    @Test
    public void insertMonetaryAccountWithTransaction_retrieveAssociatedCustomerAccount_expectCustomerAccountWithTransactionData() throws SQLException {
        Dao<CustomerAccount, Long> DAOCustomerAccount = inMemoryDBManager.getDaoForClass(CustomerAccount.class);

        Currency dollar = new Currency("USD", "Dollar");
        Customer customer = new Customer("Matias", "Juarez");
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setCustomer(customer);
        customerAccount.setBaseCountry(new Country("AR", "Argentina"));

        MonetaryAccount monetaryAccount = new MonetaryAccount();
        monetaryAccount.setAccountCurrency(dollar);
        monetaryAccount.setCustomerAccount(customerAccount);

        MonetaryAccount targetAccount = new MonetaryAccount();
        targetAccount.setAccountCurrency(dollar);
        targetAccount.setCustomerAccount(customerAccount);

        customerAccount.addMonetaryAccount(monetaryAccount);
        customerAccount.addMonetaryAccount(targetAccount);

        Transaction transaction = new Transaction(monetaryAccount, targetAccount, new Money("100.00", dollar),
                new Money("10.0", dollar), new Money("90.0", dollar), new Date());

        monetaryAccount.addTransaction(transaction);
        targetAccount.addTransaction(transaction);

        DataSaveHelper.saveCustomerAccount(customerAccount);

        CustomerAccount retrievedAccount = DAOCustomerAccount.queryForId(1L);

        Assert.assertNotNull(retrievedAccount);
    }

    @Test
    @Ignore
    public void testServer() throws Exception {
        Server webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

        Dao<CustomerAccount, Long> DAOCustomerAccount = inMemoryDBManager.getDaoForClass(CustomerAccount.class);

        Currency dollar = new Currency("USD", "Dollar");
        Customer customer = new Customer("Matias", "Juarez");
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setCustomer(customer);
        customerAccount.setBaseCountry(new Country("AR", "Argentina"));

        MonetaryAccount monetaryAccount = new MonetaryAccount();
        monetaryAccount.setAccountCurrency(dollar);
        monetaryAccount.setCustomerAccount(customerAccount);

        MonetaryAccount targetAccount = new MonetaryAccount();
        targetAccount.setAccountCurrency(dollar);
        targetAccount.setCustomerAccount(customerAccount);

        customerAccount.addMonetaryAccount(monetaryAccount);
        customerAccount.addMonetaryAccount(targetAccount);

        Transaction transaction = new Transaction(monetaryAccount, targetAccount, new Money("100.00", dollar),
                new Money("10.0", dollar), new Money("90.0", dollar), new Date());

        monetaryAccount.addTransaction(transaction);
        targetAccount.addTransaction(transaction);

        DataSaveHelper.saveCustomerAccount(customerAccount);

        CustomerAccount retrievedAccount = DAOCustomerAccount.queryForId(1L);

        Thread.sleep(9999999);
    }
}
