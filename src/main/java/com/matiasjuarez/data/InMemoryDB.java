package com.matiasjuarez.data;

import lombok.Data;

@Data
public abstract class InMemoryDB {
    private String id;

    public InMemoryDB(String id) {
        this.id = id;
    }

    public abstract void loadData();
    public abstract void clearData();
}
