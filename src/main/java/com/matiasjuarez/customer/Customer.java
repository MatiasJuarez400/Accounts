package com.matiasjuarez.customer;

import lombok.Data;

@Data
public class Customer {
    private String id;
    private String name;
    private String lastname;
    private Country country;
}
