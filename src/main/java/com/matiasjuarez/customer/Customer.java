package com.matiasjuarez.customer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "customers")
public class Customer {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String lastname;

    private Customer() {}

    public Customer(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
