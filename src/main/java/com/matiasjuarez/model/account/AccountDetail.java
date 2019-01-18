package com.matiasjuarez.model.account;

import com.matiasjuarez.model.transaction.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class AccountDetail {
    private Account account;
    private List<Transaction> transactions;
}
