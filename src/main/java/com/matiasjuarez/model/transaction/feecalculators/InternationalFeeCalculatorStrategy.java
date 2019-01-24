package com.matiasjuarez.model.transaction.feecalculators;

import com.matiasjuarez.model.customer.Country;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;

import java.math.BigDecimal;

/**
 * Logic to calculate fees on international transactions
 */
public class InternationalFeeCalculatorStrategy implements FeeCalculatorStrategy {
    @Override
    public BigDecimal calculate(MonetaryAccount origin, MonetaryAccount target, BigDecimal monetaryAmount) {
        Country originCountry = origin.getCustomerAccount().getBaseCountry();
        Country targetCountry = target.getCustomerAccount().getBaseCountry();

        if (originCountry.getCode().equalsIgnoreCase(targetCountry.getCode())) {
            return BigDecimal.ZERO;
        } else {
            return monetaryAmount.multiply(new BigDecimal(0.10));
        }
    }
}
