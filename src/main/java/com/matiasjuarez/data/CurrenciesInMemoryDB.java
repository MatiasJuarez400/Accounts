package com.matiasjuarez.data;

import com.matiasjuarez.money.Currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrenciesInMemoryDB extends InMemoryDB {
    public static final String ID = "CURRENCIES_DB";

    private Map<String, Currency> currencyExchangeRatesMap;

    public CurrenciesInMemoryDB() {
        super(ID);
        this.currencyExchangeRatesMap = new HashMap<>();
    }

    @Override
    public void loadData() {
        addCurrency(new Currency("ARS", "Argentine Peso", 0.027));
        addCurrency(new Currency("GBP", "British Pound", 1.30));
        addCurrency(new Currency("USD", "United States Dollar ", 1));
        addCurrency(new Currency("RUB", "Russian Ruble", 0.015));
        addCurrency(new Currency("EUR", "Euro", 1.14));
        addCurrency(new Currency("JPY", "Japanese Yen", 0.0091));
        addCurrency(new Currency("BRL", "Brazilian Real", 0.27));
    }

    private void addCurrency(Currency currency) {
        currencyExchangeRatesMap.put(currency.getTicker(), currency);
    }

    @Override
    public void clearData() {
        currencyExchangeRatesMap.clear();
    }

    public List<Currency> getCurrencies() {
        return new ArrayList<>(currencyExchangeRatesMap.values());
    }

    public Currency getCurrency(String ticker) {
        return currencyExchangeRatesMap.get(ticker);
    }
}
