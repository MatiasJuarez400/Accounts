package com.matiasjuarez.monetaryaccount.transaction;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class TransactionErrorMessageModels {
    private String notActiveMonetaryAccountError;

    private static TransactionErrorMessageModels instance;

    private TransactionErrorMessageModels() {
       Properties properties = new Properties();
        try {
            try(final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("transactionErrorsModels.properties")) {
                properties.load(stream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        notActiveMonetaryAccountError = properties.getProperty("notActiveMonetaryAccountError");
    }

    public static TransactionErrorMessageModels getInstance() {
        if (instance == null) {
            instance = new TransactionErrorMessageModels();
        }

        return instance;
    }
}
