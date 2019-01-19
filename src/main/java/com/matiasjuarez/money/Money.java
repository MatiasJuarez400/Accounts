package com.matiasjuarez.money;

import java.io.Serializable;
import java.math.BigDecimal;

public class Money implements Serializable {
    private BigDecimal amount;
    private String currencyCode;

    public Money(BigDecimal amount, String currencyCode) {
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public Money() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }


}
