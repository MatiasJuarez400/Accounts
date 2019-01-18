package com.matiasjuarez.money;

import com.matiasjuarez.money.dao.CurrencyDAO;

import java.math.BigDecimal;

public class MoneyConverter {
    private CurrencyDAO currencyDAO;

    public MoneyConverter(CurrencyDAO currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    public double getExchangeRate(Currency origin, Currency target) {
        return getExchangeRate(origin.getTicker(), target.getTicker());
    }

    public double getExchangeRate(String tickerOrigin, String tickerTarget) {
        Double originExchangeRate = currencyDAO.getToDollarExchangeRate(tickerOrigin);
        Double targetExchangeRate = currencyDAO.getToDollarExchangeRate(tickerTarget);

        if (originExchangeRate == null || targetExchangeRate == null) {
            throw new IllegalArgumentException("It was not possible to retrieve the exchange rate for one or both tickers");
        }

        return originExchangeRate / targetExchangeRate;
    }

    public Money convertMoney(Money origin, Currency target) {
        double exchangeRate = getExchangeRate(origin.getCurrency(), target);

        Money convertedMoney = new Money();
        convertedMoney.setAmount(origin.getAmount().multiply(new BigDecimal(exchangeRate)));
        convertedMoney.setCurrency(target);

        return convertedMoney;
    }
}
