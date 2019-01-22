package com.matiasjuarez.api;

import com.matiasjuarez.data.InMemoryDBManager;

public abstract class BaseService {
    protected InMemoryDBManager inMemoryDBManager;

    public BaseService() {
        this.inMemoryDBManager = InMemoryDBManager.getInstance();
    }
}
