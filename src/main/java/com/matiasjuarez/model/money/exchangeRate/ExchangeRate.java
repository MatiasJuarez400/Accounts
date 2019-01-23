package com.matiasjuarez.model.money.exchangeRate;

import java.math.BigDecimal;

public class ExchangeRate {
    private String tickerOrigin;
    private String tickerTarget;
    private BigDecimal rate;
    private BigDecimal invertedRate;

    public ExchangeRate(String tickerOrigin, String tickerTarget, BigDecimal rate) {
        this.tickerOrigin = tickerOrigin;
        this.tickerTarget = tickerTarget;
        setRate(rate);
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
        this.invertedRate = BigDecimal.ONE.divide(this.rate, 2, BigDecimal.ROUND_HALF_UP);
    }

    public String getTickerOrigin() {
        return tickerOrigin;
    }

    public String getTickerTarget() {
        return tickerTarget;
    }

    public BigDecimal getInvertedRate() {
        return invertedRate;
    }
}
