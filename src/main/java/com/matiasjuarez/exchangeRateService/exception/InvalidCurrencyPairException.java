package com.matiasjuarez.exchangeRateService.exception;

public class InvalidCurrencyPairException extends IllegalArgumentException {
    public InvalidCurrencyPairException(String currencyPair) {
        super("The currencyPair requested: " + currencyPair + " is not supported yet, use a valid currencyPair");
    }
}
