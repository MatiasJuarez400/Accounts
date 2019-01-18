package com.matiasjuarez.money.dao;

import com.matiasjuarez.money.Currency;

import java.util.List;

public interface CurrencyDAO {
    List<Currency> getCurrencies();
    Currency getCurrency(String ticker);
}
