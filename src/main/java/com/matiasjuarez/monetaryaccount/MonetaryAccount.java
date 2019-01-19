package com.matiasjuarez.monetaryaccount;

import com.matiasjuarez.monetaryaccount.transaction.Transaction;
import com.matiasjuarez.money.Money;

import java.util.ArrayList;
import java.util.List;


public class MonetaryAccount {
    private String id;
    private Money money;
    private MonetaryAccountStatus accountStatus;
    private List<Transaction> transactions;

    public MonetaryAccount() {
        this.transactions = new ArrayList<>();
    }

    public boolean isOperative() {
        return this.accountStatus == MonetaryAccountStatus.OPERATIVE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public MonetaryAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(MonetaryAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
