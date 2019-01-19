package com.matiasjuarez.monetaryaccount;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.customer.CustomerAccount;
import com.matiasjuarez.monetaryaccount.transaction.Transaction;
import com.matiasjuarez.money.Money;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DatabaseTable(tableName = "monetary_accounts")
public class MonetaryAccount {
    @DatabaseField(generatedId = true)
    private long id;
//    @DatabaseField(persisterClass = MoneyPersister.class)
//    private Money money;
    @DatabaseField
    private MonetaryAccountStatus accountStatus;
    @ForeignCollectionField
    private Collection<Transaction> transactions;
    @DatabaseField(foreign = true)
    private CustomerAccount customerAccount;

    public MonetaryAccount() {
        this.transactions = new ArrayList<>();
    }

    public boolean isOperative() {
        return this.accountStatus == MonetaryAccountStatus.OPERATIVE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
//
//    public Money getMoney() {
//        return money;
//    }
//
//    public void setMoney(Money money) {
//        this.money = money;
//    }

    public MonetaryAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(MonetaryAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<Transaction> getTransactions() {
        return (ArrayList) transactions;
    }
}
