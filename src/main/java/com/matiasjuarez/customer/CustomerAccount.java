package com.matiasjuarez.customer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.monetaryaccount.MonetaryAccount;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DatabaseTable(tableName = "customer_accounts")
public class CustomerAccount {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(foreign = true, foreignAutoCreate = true)
    private Customer customer;
    @ForeignCollectionField
    private Collection<MonetaryAccount> monetaryAccounts;
    @DatabaseField(foreign = true, foreignAutoCreate = true)
    private Country baseCountry;

    private CustomerAccount() {}

    public CustomerAccount(Customer customer, Country baseCountry) {
        this.customer = customer;
        this.baseCountry = baseCountry;
        this.monetaryAccounts = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Country getBaseCountry() {
        return baseCountry;
    }

    public void setBaseCountry(Country baseCountry) {
        this.baseCountry = baseCountry;
    }

    public Collection<MonetaryAccount> getMonetaryAccounts() {
        return monetaryAccounts;
    }

    public void addMonetaryAccount(MonetaryAccount monetaryAccount) {
        this.monetaryAccounts.add(monetaryAccount);
    }
}
