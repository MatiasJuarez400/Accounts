package com.matiasjuarez.money;

import com.matiasjuarez.money.model.Currency;

import java.sql.SQLException;
import java.util.List;

public interface CurrencyService {
    List<Currency> getCurrencies() throws SQLException;
    Currency getCurrency(String ticker) throws SQLException;
}
