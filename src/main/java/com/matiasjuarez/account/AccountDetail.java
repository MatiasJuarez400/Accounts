package com.matiasjuarez.account;

import com.matiasjuarez.transaction.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class AccountDetail {
    private Account account;
    private List<Transaction> transactions;
}
