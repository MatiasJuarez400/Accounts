package com.matiasjuarez.api.monetaryaccount;

import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface MonetaryAccountService {
    MonetaryAccount createMonetaryAccount(MonetaryAccount monetaryAccount) throws SQLException;
    List<MonetaryAccount> getMonetaryAccountsFromCustomerAccount(Long customerAccountId) throws SQLException;
    MonetaryAccount updateMonetaryAccount(MonetaryAccount monetaryAccountUpdateData) throws SQLException;
    MonetaryAccount getMonetaryAccount(Long monetaryAccountId) throws SQLException;
    void processTransaction(Transaction transaction) throws Exception;
}
