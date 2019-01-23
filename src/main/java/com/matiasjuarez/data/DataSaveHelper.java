package com.matiasjuarez.data;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.monetaryaccount.transaction.Transaction;

import java.sql.SQLException;

public class DataSaveHelper {
    public static CustomerAccount saveCustomerAccount(CustomerAccount customerAccount) {
        Dao<CustomerAccount, Long> dao = InMemoryDBManager.getInstance().getDaoForClass(CustomerAccount.class);
        try {
            dao.createOrUpdate(customerAccount);
            for (MonetaryAccount monetaryAccount : customerAccount.getMonetaryAccounts()) {
                saveMonetaryAccount(monetaryAccount);
            }

            return customerAccount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static MonetaryAccount saveMonetaryAccount(MonetaryAccount monetaryAccount) {
        Dao<MonetaryAccount, Long> dao = InMemoryDBManager.getInstance().getDaoForClass(MonetaryAccount.class);
        try {
            dao.createOrUpdate(monetaryAccount);
            for (Transaction transaction : monetaryAccount.getTransactions()) {
                saveTransaction(transaction);
            }
            return monetaryAccount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Transaction saveTransaction(Transaction transaction) {
        Dao<Transaction, Long> dao = InMemoryDBManager.getInstance().getDaoForClass(Transaction.class);
        try {
            dao.createOrUpdate(transaction);
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
