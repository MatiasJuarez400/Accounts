package com.matiasjuarez.model.monetaryaccount.transaction.feecalculators;

import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;

import java.math.BigDecimal;

public interface FeeCalculatorStrategy {
    BigDecimal calculate(MonetaryAccount origin, MonetaryAccount target, BigDecimal monetaryAmount);
}
