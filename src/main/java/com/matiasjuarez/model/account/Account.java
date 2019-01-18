package com.matiasjuarez.model.account;

import com.matiasjuarez.model.Country;
import com.matiasjuarez.model.Customer;
import lombok.Data;

@Data
public class Account {
    private String id;
    private Country baseCountry;
    private Customer owner;
}
