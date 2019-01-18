package com.matiasjuarez.account;

import com.matiasjuarez.Country;
import com.matiasjuarez.Customer;
import lombok.Data;

import java.util.List;

@Data
public class Account {
    private String id;
    private Country baseCountry;
    private Customer owner;
    private List<Balance> balances;
}
