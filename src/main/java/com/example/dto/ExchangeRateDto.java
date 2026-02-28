package com.example.dto;

import java.math.BigDecimal;

public record ExchangeRateDto(long id, int baseCurrencyId, int targetCurrencyId, BigDecimal rate) {

    public long getId() {
        return id;
    }

    public int getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public int getTargetCurrencyId() {
        return targetCurrencyId;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
