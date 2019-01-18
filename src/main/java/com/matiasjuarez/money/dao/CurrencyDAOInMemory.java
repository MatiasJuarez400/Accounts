package com.matiasjuarez.money.dao;

import com.matiasjuarez.data.InMemoryDBsManager;
import com.matiasjuarez.money.Currency;

import java.util.List;

public class CurrencyDAOInMemory implements CurrencyDAO {

    @Override
    public List<Currency> getCurrencies() {
        return InMemoryDBsManager.getInstance().getCurrenciesInMemoryDB().getCurrencies();
    }

    @Override
    public Currency getCurrency(String ticker) {
        return InMemoryDBsManager.getInstance().getCurrenciesInMemoryDB().getCurrency(ticker);
    }
}
