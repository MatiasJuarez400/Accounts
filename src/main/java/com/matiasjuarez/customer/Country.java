package com.matiasjuarez.customer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "countries")
public class Country {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String code;
    @DatabaseField
    private String name;

    private Country() {}

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
