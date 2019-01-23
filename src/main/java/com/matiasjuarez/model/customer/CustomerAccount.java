package com.matiasjuarez.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "customer_accounts")
public class CustomerAccount {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Customer customer;
    @ForeignCollectionField
    @JsonIgnore
    private Collection<MonetaryAccount> monetaryAccounts;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Country baseCountry;

    public CustomerAccount() {
        this.monetaryAccounts = new ArrayList<>();
    }

    public CustomerAccount(Long id) {
        this.id = id;
    }

    public void addMonetaryAccount(MonetaryAccount monetaryAccount) {
        this.monetaryAccounts.add(monetaryAccount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Collection<MonetaryAccount> getMonetaryAccounts() {
        return monetaryAccounts;
    }

    public void setMonetaryAccounts(Collection<MonetaryAccount> monetaryAccounts) {
        this.monetaryAccounts = monetaryAccounts;
    }

    public Country getBaseCountry() {
        return baseCountry;
    }

    public void setBaseCountry(Country baseCountry) {
        this.baseCountry = baseCountry;
    }
}
