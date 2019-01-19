package com.matiasjuarez.customer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.monetaryaccount.MonetaryAccount;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DatabaseTable(tableName = "customerAccounts")
public class CustomerAccount {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(foreign = true)
    private Customer customer;
    @ForeignCollectionField
    private Collection<MonetaryAccount> monetaryAccounts;
    @DatabaseField(foreign = true)
    private Country baseCountry;

    public CustomerAccount(Customer customer, Country baseCountry) {
        this.customer = customer;
        this.baseCountry = baseCountry;
        this.monetaryAccounts = new ArrayList<>();
    }

    public CustomerAccount() {}

    public void addMonetaryAccount(MonetaryAccount monetaryAccount) {
        this.monetaryAccounts.add(monetaryAccount);
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<MonetaryAccount> getMonetaryAccounts() {
        return (ArrayList) monetaryAccounts;
    }

    public Country getBaseCountry() {
        return baseCountry;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
