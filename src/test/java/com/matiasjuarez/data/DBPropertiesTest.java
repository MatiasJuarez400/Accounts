package com.matiasjuarez.data;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class DBPropertiesTest {

    @Test
    public void initializeDBPropertiesFromPropertiesFile_expectNoEmptyFields() {
        DBProperties dbProperties = DBProperties.getInstance();

        Assert.assertNotNull(dbProperties);
        Assert.assertTrue(StringUtils.isNotEmpty(dbProperties.getDbURL()));
        Assert.assertTrue(StringUtils.isNotEmpty(dbProperties.getUsername()));
        Assert.assertTrue(StringUtils.isNotEmpty(dbProperties.getPassword()));
    }
}
