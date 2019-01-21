package com.matiasjuarez.monetaryaccount;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.customer.CustomerAccount;
import com.matiasjuarez.monetaryaccount.transaction.Transaction;
import com.matiasjuarez.money.model.Currency;
import com.matiasjuarez.money.model.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@DatabaseTable(tableName = "monetary_accounts")
public class MonetaryAccount {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private BigDecimal funds;
    @DatabaseField(foreign = true, foreignAutoCreate = true)
    private Currency accountCurrency;
    @DatabaseField
    private MonetaryAccountStatus accountStatus;
    @ForeignCollectionField(foreignFieldName = "originAccount")
    private Collection<Transaction> transactions;
    @DatabaseField(foreign = true)
    private CustomerAccount customerAccount;

    private MonetaryAccount() {}

    public MonetaryAccount(Currency accountCurrency, CustomerAccount customerAccount, MonetaryAccountStatus initialStatus) {
        this.accountCurrency = accountCurrency;
        this.customerAccount = customerAccount;
        this.accountStatus = initialStatus;
        this.funds = new BigDecimal(0.0);
        this.transactions = new ArrayList<>();
    }

    public MonetaryAccount(Currency accountCurrency, CustomerAccount customerAccount) {
        this(accountCurrency, customerAccount, MonetaryAccountStatus.OPERATIVE);
    }

    public void setMoney(Money money) {
        if (money.getCurrency() != this.accountCurrency) {
            throw new IllegalArgumentException("The currency of the monetaryAccount differs from the currency of the money object");
        }

        this.funds = money.getAmount();
    }

    public boolean isOperative() {
        return this.accountStatus == MonetaryAccountStatus.OPERATIVE;
    }

    public void freeze() {
        this.accountStatus = MonetaryAccountStatus.FROZEN;
    }

    public void deactivate() {
        this.accountStatus = MonetaryAccountStatus.INACTIVE;
    }

    public void activate() {
        this.accountStatus = MonetaryAccountStatus.OPERATIVE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Money getFunds() {
        return new Money(this.funds, this.accountCurrency);
    }

    public MonetaryAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public List<Transaction> getTransactions() {
        return (ArrayList) transactions;
    }

    public boolean addTransaction(Transaction transaction) {
        return this.transactions.add(transaction);
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
        customerAccount.addMonetaryAccount(this);
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
