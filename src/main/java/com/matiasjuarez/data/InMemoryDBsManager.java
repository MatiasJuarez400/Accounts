package com.matiasjuarez.data;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDBsManager {
    private Map<String, InMemoryDB> inMemoryDBMap;

    private static InMemoryDBsManager inMemoryDBsManager;

    private InMemoryDBsManager() {
        this.inMemoryDBMap = new HashMap<>();
        addInMemoryDB(new CurrenciesInMemoryDB());
    }

    private void addInMemoryDB(InMemoryDB inMemoryDB) {
        inMemoryDBMap.put(inMemoryDB.getId(), inMemoryDB);
    }

    public void initializeInMemoryDBs() {
        for (InMemoryDB inMemoryDB : inMemoryDBMap.values()) {
            inMemoryDB.loadData();
        }
    }

    public static InMemoryDBsManager getInstance() {
        if (inMemoryDBsManager == null) {
            inMemoryDBsManager = new InMemoryDBsManager();
        }

        return inMemoryDBsManager;
    }

    public InMemoryDB getDB(String dbId) {
        return inMemoryDBMap.get(dbId);
    }

    public CurrenciesInMemoryDB getCurrenciesInMemoryDB() {
        return (CurrenciesInMemoryDB) getDB(CurrenciesInMemoryDB.ID);
    }
}
