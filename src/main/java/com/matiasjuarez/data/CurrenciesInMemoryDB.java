package com.matiasjuarez.data;

import com.matiasjuarez.money.Currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrenciesInMemoryDB extends InMemoryDB {
    public static final String ID = "CURRENCIES_DB";

    private Map<String, Currency> currenciesMap;
    private Map<String, Double> currencyToDollarExchangeRatesMap;

    public CurrenciesInMemoryDB() {
        super(ID);
        this.currenciesMap = new HashMap<>();
        this.currencyToDollarExchangeRatesMap = new HashMap<>();
    }

    @Override
    public void loadData() {
        addCurrencyData("ARS", "Argentine Peso", 0.027);
        addCurrencyData("GBP", "British Pound", 1.30);
        addCurrencyData("USD", "United States Dollar ", 1);
        addCurrencyData("RUB", "Russian Ruble", 0.015);
        addCurrencyData("EUR", "Euro", 1.14);
        addCurrencyData("JPY", "Japanese Yen", 0.0091);
        addCurrencyData("BRL", "Brazilian Real", 0.27);
    }

    private void addCurrencyData(String ticker, String currencyName, double toDollarExchangeRate) {
        currenciesMap.put(ticker, new Currency(ticker, currencyName));
        currencyToDollarExchangeRatesMap.put(ticker, toDollarExchangeRate);
    }

    @Override
    public void clearData() {
        currenciesMap.clear();
    }

    public List<Currency> getCurrencies() {
        return new ArrayList<>(currenciesMap.values());
    }

    public Currency getCurrency(String ticker) {
        return currenciesMap.get(ticker);
    }

    public double getToDollarExchangeRate(String ticker) {
        return currencyToDollarExchangeRatesMap.get(ticker);
    }
}
