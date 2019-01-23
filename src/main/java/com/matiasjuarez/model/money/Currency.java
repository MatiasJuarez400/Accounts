package com.matiasjuarez.model.money;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "currencies")
public class Currency {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String ticker;
    @DatabaseField
    private String name;

    public Currency() {}

    public Currency(String ticker) {
        this.ticker = ticker;
    }

    public Currency(String ticker, String name) {
        this.ticker = ticker;
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
