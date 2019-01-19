package com.matiasjuarez.data;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class InMemoryDBManagerTest {

    @Test
    public void getConnectionFromManager_expectNotNullConnectionObject() {
        InMemoryDBManager inMemoryDBManager = InMemoryDBManager.getInstance();

        Connection connection = inMemoryDBManager.getConnection();

        Assert.assertNotNull(connection);
    }
}
