package com.example.dto;

import java.math.BigDecimal;

public record ExchangeRateResponseDto(long id, int baseCurrencyId, int targetCurrencyId, BigDecimal rate) {
}
