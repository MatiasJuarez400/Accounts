package com.matiasjuarez.model.money.exchangeRate;

public class ExchangeRateRaw {
    private String tickerOrigin;
    private String tickerTarget;
    private String rate;

    public String getTickerOrigin() {
        return tickerOrigin;
    }

    public void setTickerOrigin(String tickerOrigin) {
        this.tickerOrigin = tickerOrigin;
    }

    public String getTickerTarget() {
        return tickerTarget;
    }

    public void setTickerTarget(String tickerTarget) {
        this.tickerTarget = tickerTarget;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
