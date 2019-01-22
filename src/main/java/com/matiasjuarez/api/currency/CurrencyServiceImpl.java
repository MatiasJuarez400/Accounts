package com.matiasjuarez.api.currency;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.data.InMemoryDBManager;
import com.matiasjuarez.money.Currency;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyServiceImpl extends BaseService implements CurrencyService {
    public List<Currency> getCurrencies() throws SQLException {
        Dao<Currency, Long> currencyDao = inMemoryDBManager.getDaoForClass(Currency.class);
        return currencyDao.queryForAll();
    }

    @Override
    public Currency getCurrency(String ticker) throws SQLException {
        Dao<Currency, Long> currencyDao = inMemoryDBManager.getDaoForClass(Currency.class);

        Map<String, Object> queryFields = new HashMap<>();
        queryFields.put("ticker", ticker);

        List<Currency> currenciesReturned = currencyDao.queryForFieldValues(queryFields);

        return currenciesReturned.size() > 0 ? currenciesReturned.get(0) : null;
    }
}
