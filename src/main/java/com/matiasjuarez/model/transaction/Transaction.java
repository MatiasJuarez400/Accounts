package com.matiasjuarez.model.transaction;

import com.matiasjuarez.model.Money;
import lombok.Data;

import java.util.Date;

@Data
public class Transaction {
    private String originAccount;
    private String destinyAccount;
    private Money transferedMoney;
    private Date executionDate;
    private TransactionStatus transactionStatus;
    private TransactionError transactionError;
}
