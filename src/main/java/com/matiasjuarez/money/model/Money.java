package com.matiasjuarez.money.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Money implements Serializable {
    private BigDecimal amount;
    private Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money(String amount, Currency currency) {
        this(new BigDecimal(amount), currency);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }


}
