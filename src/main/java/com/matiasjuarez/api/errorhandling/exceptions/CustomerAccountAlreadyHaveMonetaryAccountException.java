package com.matiasjuarez.api.errorhandling.exceptions;

public class CustomerAccountAlreadyHaveMonetaryAccountException extends RuntimeException {
    private static final String ERROR_MODEL = "Customer account with id [%s] already has a monetary account with currency [%s]";

    public CustomerAccountAlreadyHaveMonetaryAccountException(Long customerAccountId, String currencyTicker) {
        super(String.format(ERROR_MODEL, customerAccountId, currencyTicker));
    }
}
