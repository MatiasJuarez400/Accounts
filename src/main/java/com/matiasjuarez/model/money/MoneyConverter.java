package com.matiasjuarez.model.money;

import com.matiasjuarez.model.money.exchangeRate.ExchangeRate;
import com.matiasjuarez.model.money.exchangeRate.ExchangeRateService;
import com.matiasjuarez.utils.DecimalRounder;

import java.math.BigDecimal;

public class MoneyConverter {
    private ExchangeRateService exchangeRateService;

    public MoneyConverter(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public Money convertMoney(Money money, Currency targetCurrency) {
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(money.getCurrency().getTicker(), targetCurrency.getTicker());

        BigDecimal rate = exchangeRate.getTickerOrigin().equalsIgnoreCase(money.getCurrency().getTicker()) ?
                exchangeRate.getRate() : exchangeRate.getInvertedRate();

        BigDecimal convertedMoneyValue = money.getAmount().multiply(rate);

        return new Money(DecimalRounder.round(convertedMoneyValue), targetCurrency);
    }
}
