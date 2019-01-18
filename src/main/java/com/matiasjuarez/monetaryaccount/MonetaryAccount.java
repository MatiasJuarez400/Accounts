package com.matiasjuarez.monetaryaccount;

import com.matiasjuarez.monetaryaccount.transaction.Transaction;
import com.matiasjuarez.money.Money;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MonetaryAccount {
    private String id;
    private Money money;
    private MonetaryAccountStatus accountStatus;
    private List<Transaction> transactions;

    public MonetaryAccount() {
        this.transactions = new ArrayList<>();
    }

    public boolean isOperative() {
        return this.accountStatus == MonetaryAccountStatus.OPERATIVE;
    }
}
