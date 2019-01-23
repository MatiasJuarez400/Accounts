package com.matiasjuarez.model.monetaryaccount.transaction.feecalculators;

import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Logic to calculate currency conversion and international fees on the amount to transfer
 */
public class BasicFeeCalculatorStrategy implements FeeCalculatorStrategy {
    private List<FeeCalculatorStrategy> feeCalculatorStrategies;

    @Inject
    public BasicFeeCalculatorStrategy(InternationalFeeCalculatorStrategy internationalFeeCalculatorStrategy,
                                      CurrencyConversionFeeCalcultorStrategy currencyConversionFeeCalcultorStrategy) {
        this.feeCalculatorStrategies = new ArrayList<>();
        this.feeCalculatorStrategies.add(currencyConversionFeeCalcultorStrategy);
        this.feeCalculatorStrategies.add(internationalFeeCalculatorStrategy);
    }

    @Override
    public BigDecimal calculate(MonetaryAccount origin, MonetaryAccount target, BigDecimal monetaryAmount) {
        BigDecimal feesToApply = BigDecimal.ZERO;
        BigDecimal monetaryAmountLeft = monetaryAmount;

        for (FeeCalculatorStrategy feeCalculatorStrategy : feeCalculatorStrategies) {
            BigDecimal strategyFee = feeCalculatorStrategy.calculate(origin, target, monetaryAmountLeft);

            feesToApply.add(strategyFee);
            monetaryAmountLeft.subtract(strategyFee);
        }

        return feesToApply;
    }
}
