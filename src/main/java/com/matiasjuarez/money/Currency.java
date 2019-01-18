package com.matiasjuarez.money;

import lombok.Data;

@Data
public class Currency {
    private String ticker;
    private String name;
    private double toDollarExchangeRate;

    public Currency(String ticker, String name, double toDollarExchangeRate) {
        this.ticker = ticker;
        this.name = name;
        this.toDollarExchangeRate = toDollarExchangeRate;
    }
}
