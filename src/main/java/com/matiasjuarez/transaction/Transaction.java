package com.matiasjuarez.transaction;

import com.matiasjuarez.money.Money;
import lombok.Data;

import java.util.Date;

@Data
public class Transaction {
    private String originAccount;
    private String destinyAccount;
    private Money transferedMoney;
    private Money fees;
    private Money effectivelyTransferedMoney;
    private Date executionDate;
    private TransactionStatus transactionStatus;
    private TransactionError transactionError;
}
