package com.matiasjuarez.api.transaction;

import java.math.BigDecimal;

public class TransactionDTO {
    private Long originMonetaryAccountId;
    private Long targetMonetaryAccountId;
    private BigDecimal amount;

    public Long getOriginMonetaryAccountId() {
        return originMonetaryAccountId;
    }

    public void setOriginMonetaryAccountId(Long originMonetaryAccountId) {
        this.originMonetaryAccountId = originMonetaryAccountId;
    }

    public Long getTargetMonetaryAccountId() {
        return targetMonetaryAccountId;
    }

    public void setTargetMonetaryAccountId(Long targetMonetaryAccountId) {
        this.targetMonetaryAccountId = targetMonetaryAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
