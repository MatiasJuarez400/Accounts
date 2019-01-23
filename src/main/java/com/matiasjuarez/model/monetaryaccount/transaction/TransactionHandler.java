package com.matiasjuarez.model.monetaryaccount.transaction;

import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.monetaryaccount.transaction.feecalculators.FeeCalculatorStrategy;
import com.matiasjuarez.model.money.Money;
import com.matiasjuarez.model.money.MoneyConverter;
import com.matiasjuarez.utils.DecimalRounder;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionHandler {
    private FeeCalculatorStrategy feeCalculatorStrategy;
    private MoneyConverter moneyConverter;

    public TransactionHandler(FeeCalculatorStrategy feeCalculatorStrategy, MoneyConverter moneyConverter) {
        this.feeCalculatorStrategy = feeCalculatorStrategy;
        this.moneyConverter = moneyConverter;
    }

    public boolean canExecuteTransaction(MonetaryAccount origin, MonetaryAccount target, BigDecimal amountToTransfer) {
        TransactionError transactionError = getTransactionError(origin, target, amountToTransfer);

        return transactionError == null;
    }

    public TransactionError getTransactionError(MonetaryAccount origin, MonetaryAccount target, BigDecimal amountToTransfer) {
        String error = null;
        TransactionError transactionError = null;

        if (!origin.isOperative()) {
            error = "Origin monetary account is not operative";
        } else if (!target.isOperative()) {
            error = "Target monetary account is not operative";
        } else if (origin.getFunds().compareTo(amountToTransfer) < 0) {
            error = "Insufficient funds";
        }

        if (error != null) {
            transactionError = new TransactionError(origin, target, amountToTransfer, new Date(), error);
        }

        return transactionError;
    }

    public Transaction buildTransaction(MonetaryAccount origin, MonetaryAccount target, BigDecimal amountToTransfer) {
        BigDecimal feeToApply = feeCalculatorStrategy.calculate(origin, target, amountToTransfer);
        BigDecimal effectiveAmount = amountToTransfer.subtract(feeToApply);

        Money initialAmountMoney = new Money(DecimalRounder.round(amountToTransfer), origin.getAccountCurrency());
        Money feesMoney = new Money(DecimalRounder.round(feeToApply), origin.getAccountCurrency());
        Money effectiveAmountMoney = new Money(effectiveAmount, origin.getAccountCurrency());
        Money convertedEffectiveAmountMoney = moneyConverter.convertMoney(effectiveAmountMoney, target.getAccountCurrency());

        return new Transaction(origin, target, initialAmountMoney, feesMoney, convertedEffectiveAmountMoney, new Date());
    }
}
