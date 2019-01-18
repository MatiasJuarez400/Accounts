package com.matiasjuarez.money;

import java.math.BigDecimal;

public class MoneyConverter {

    public double getExchangeRate(Currency origin, Currency destiny) {
        return origin.getToDollarExchangeRate() / destiny.getToDollarExchangeRate();
    }

    public Money convertMoney(Money origin, Currency destiny) {
        double exchangeRate = getExchangeRate(origin.getCurrency(), destiny);

        Money convertedMoney = new Money();
        convertedMoney.setAmount(origin.getAmount().multiply(new BigDecimal(exchangeRate)));
        convertedMoney.setCurrency(destiny);

        return convertedMoney;
    }
}
