package com.matiasjuarez.api.currency;

import com.matiasjuarez.money.Currency;

import java.sql.SQLException;
import java.util.List;

public interface CurrencyService {
    List<Currency> getCurrencies() throws SQLException;
    Currency getCurrency(String ticker) throws SQLException;
}
