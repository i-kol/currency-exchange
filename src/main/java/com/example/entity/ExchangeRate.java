package com.example.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class ExchangeRate {
    private long id;
    private int baseCurrencyId;
    private int targetCurrencyId;
    private BigDecimal rate;
}
