package com.matiasjuarez.money;

import java.math.BigDecimal;

public class ExchangeRate {
    String currencyPair;
    BigDecimal bid;
    BigDecimal offer;

    public ExchangeRate(String currencyPair, BigDecimal bid, BigDecimal offer) {
        validateArguments(currencyPair, bid, offer);
        this.currencyPair = currencyPair;
        this.bid = bid;
        this.offer = offer;
    }

    private void validateArguments(Object... constructorArgument) {
        for (Object o : constructorArgument) {
            if (null == o) {
                throw new IllegalArgumentException("ExchangeRate must be created as a valid instance");
            }
        }
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BigDecimal getOffer() {
        return offer;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("Exchange rate for currency pair ")
                .append(getCurrencyPair())
                .append(" is as follows: \n")
                .append("Bid = ").append(getBid())
                .append("\nOffer = ").append(getOffer());
        return sb.toString();
    }
}
