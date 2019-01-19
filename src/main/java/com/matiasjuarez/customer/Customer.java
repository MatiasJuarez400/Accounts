package com.matiasjuarez.customer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "customers")
public class Customer {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String lastname;

    public Customer(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
