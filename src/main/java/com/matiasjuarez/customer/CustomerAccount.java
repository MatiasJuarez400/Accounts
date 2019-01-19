package com.matiasjuarez.customer;

import com.matiasjuarez.monetaryaccount.MonetaryAccount;

import java.util.ArrayList;
import java.util.List;


public class CustomerAccount {
    private Customer customer;
    private List<MonetaryAccount> accountList;
    private Country basedOnCountry;

    public CustomerAccount() {
        this.accountList = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<MonetaryAccount> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<MonetaryAccount> accountList) {
        this.accountList = accountList;
    }

    public Country getBasedOnCountry() {
        return basedOnCountry;
    }

    public void setBasedOnCountry(Country basedOnCountry) {
        this.basedOnCountry = basedOnCountry;
    }
}
