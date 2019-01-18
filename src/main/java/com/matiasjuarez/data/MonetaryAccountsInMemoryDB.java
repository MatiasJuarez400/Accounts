package com.matiasjuarez.data;

import com.matiasjuarez.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.monetaryaccount.transaction.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonetaryAccountsInMemoryDB extends InMemoryDB {
    public static final String ID = "MONETARY_ACCOUNTS";

    private Map<String, MonetaryAccount> monetaryAccountMap;
    private Map<String, List<Transaction>> transactionsMap;

    public MonetaryAccountsInMemoryDB() {
        super(ID);
        this.monetaryAccountMap = new HashMap<>();
        this.transactionsMap = new HashMap<>();
    }

    @Override
    public void loadData() {
        
    }

    @Override
    public void clearData() {

    }

    public List<MonetaryAccount> getMonetaryAccounts() {
        return new ArrayList<>(monetaryAccountMap.values());
    }

    public MonetaryAccount getMonetaryAccount(String accountId) {
        return monetaryAccountMap.get(accountId);
    }

    public List<Transaction> getAccountTransactions(String accountId) {
        return transactionsMap.get(accountId);
    }
}
