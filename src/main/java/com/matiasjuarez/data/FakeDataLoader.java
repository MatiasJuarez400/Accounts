package com.matiasjuarez.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.customer.Country;
import com.matiasjuarez.money.model.Currency;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FakeDataLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FakeDataLoader.class);

    public void loadData() {
        try {
            loadData("data/countries.json", new TypeReference<Country>() {});
            loadData("data/currencies.json", new TypeReference<Currency>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void loadData(String resource, TypeReference<T> typeReference) throws IOException, SQLException {
        LOGGER.info("Loading data from {}", resource);

        String jsonData = readDataFromResource(resource);

        ObjectMapper objectMapper = new ObjectMapper();

        Object[] readObjects = objectMapper.readValue(jsonData, Object[].class);

        List convertedObjects = new ArrayList();
        for (Object readObject : readObjects) {
            convertedObjects.add(objectMapper.convertValue(readObject, typeReference));
        }

        Dao classDao = InMemoryDBManager.getInstance().getDaoForClassname(typeReference.getType().getTypeName());
        classDao.create(convertedObjects);
    }

    private String readDataFromResource(String resource) throws IOException {
        try(InputStream inputStream = this.getClass().
                getClassLoader().getResourceAsStream(resource)) {

            return IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        }
    }
}
