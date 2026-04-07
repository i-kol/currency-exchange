package com.example.dto;

import java.math.BigDecimal;

public record ExchangeRateRequestDto(int baseCurrencyId, int targetCurrencyId, BigDecimal rate) {
}
