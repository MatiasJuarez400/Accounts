package com.matiasjuarez.model.monetaryaccount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.matiasjuarez.model.transaction.Transaction;
import com.matiasjuarez.model.money.Currency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@DatabaseTable(tableName = "monetary_accounts")
public class MonetaryAccount {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private BigDecimal funds;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Currency accountCurrency;
    @DatabaseField
    private MonetaryAccountStatus accountStatus;
    @ForeignCollectionField
    @JsonIgnore
    private Collection<Transaction> transactions;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private CustomerAccount customerAccount;

    public MonetaryAccount() {
        this.transactions = new ArrayList<>();
    }

    public MonetaryAccount(Long id) {
        this();
        this.id = id;
    }

    public MonetaryAccount(BigDecimal funds, Currency accountCurrency, MonetaryAccountStatus accountStatus, CustomerAccount customerAccount) {
        this();
        this.funds = funds;
        this.accountCurrency = accountCurrency;
        this.accountStatus = accountStatus;
        this.customerAccount = customerAccount;
    }

    public void changeStatus(boolean isActive) {
        this.accountStatus = isActive ? MonetaryAccountStatus.OPERATIVE : MonetaryAccountStatus.INACTIVE;
    }

    public boolean isOperative() {
        return this.accountStatus == MonetaryAccountStatus.OPERATIVE;
    }

    public boolean addTransaction(Transaction transaction) {
        return this.transactions.add(transaction);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFunds() {
        return funds;
    }

    public void setFunds(BigDecimal funds) {
        this.funds = funds;
    }

    public Currency getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(Currency accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public MonetaryAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(MonetaryAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<Transaction> transactions) {
        this.transactions = transactions;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonetaryAccount that = (MonetaryAccount) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
