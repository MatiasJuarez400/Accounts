package com.matiasjuarez.customer;

import com.matiasjuarez.monetaryaccount.MonetaryAccount;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerAccount {
    private Customer customer;
    private List<MonetaryAccount> accountList;
    private Country basedOnCountry;

    public CustomerAccount() {
        this.accountList = new ArrayList<>();
    }
}
