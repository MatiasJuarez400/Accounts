package com.matiasjuarez.model.money.exchangeRate;

public interface ExchangeRateService {
    ExchangeRate getExchangeRate(String tickerOrigin, String tickerTarget);
}
