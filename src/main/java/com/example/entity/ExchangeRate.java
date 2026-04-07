package com.example.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExchangeRate {
    private long id;
    private int baseCurrencyId;
    private int targetCurrencyId;
    private BigDecimal rate;

    public ExchangeRate(int baseCurrencyId, int targetCurrencyId, BigDecimal rate) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }
}
