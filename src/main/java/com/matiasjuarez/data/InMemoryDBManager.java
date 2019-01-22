package com.matiasjuarez.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.matiasjuarez.customer.Country;
import com.matiasjuarez.customer.Customer;
import com.matiasjuarez.customer.CustomerAccount;
import com.matiasjuarez.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.monetaryaccount.transaction.Transaction;
import com.matiasjuarez.money.Currency;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDBManager {
    private static InMemoryDBManager inMemoryDBsManager;

    private DBProperties dbProperties;
    private Map<String, Dao> DAOs;

    private InMemoryDBManager() {
        this.dbProperties = DBProperties.getInstance();

        try {
            Class.forName(dbProperties.getDriverClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        initializeDAOsAndTables();
    }

    private void initializeDAOsAndTables() {
        this.DAOs = new HashMap<>();

        Class[] persistedClasses = {
                Country.class,
                Currency.class,
                Customer.class,
                CustomerAccount.class,
                MonetaryAccount.class,
                Transaction.class
        };

        ConnectionSource connectionSource = getConnectionSource();
        for (Class clazz : persistedClasses) {
            try {
                Dao newDao = (Dao) DaoManager.createDao(connectionSource, clazz);
                DAOs.put(clazz.getName(), newDao);
                TableUtils.createTable(newDao);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private ConnectionSource getConnectionSource() {
        try {
            return new JdbcConnectionSource(dbProperties.getDbURL(),
                    dbProperties.getUsername(), dbProperties.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static InMemoryDBManager getInstance() {
        if (inMemoryDBsManager == null) {
            inMemoryDBsManager = new InMemoryDBManager();
        }

        return inMemoryDBsManager;
    }

    public Dao getDaoForClass(Class clazz) {
        return getDaoForClassname(clazz.getName());
    }

    public Dao getDaoForClassname(String className) {
        return this.DAOs.get(className);
    }

    public List<Dao> getDaos() {
        return new ArrayList<>(this.DAOs.values());
    }
}
