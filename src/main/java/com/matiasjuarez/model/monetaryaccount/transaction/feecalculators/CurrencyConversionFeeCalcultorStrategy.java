package com.matiasjuarez.model.monetaryaccount.transaction.feecalculators;

import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.money.Currency;

import java.math.BigDecimal;

/**
 * Logic to calculate fees when a currency conversion has to be performed prior to the transaction execution
 */
public class CurrencyConversionFeeCalcultorStrategy implements FeeCalculatorStrategy {
    @Override
    public BigDecimal calculate(MonetaryAccount origin, MonetaryAccount target, BigDecimal monetaryAmount) {
        Currency originCurrency = origin.getAccountCurrency();
        Currency targetCurrency = target.getAccountCurrency();

        if (originCurrency.getId() == targetCurrency.getId()) {
            return BigDecimal.ZERO;
        } else {
            return monetaryAmount.multiply(new BigDecimal(0.03));
        }
    }
}
