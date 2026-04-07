package com.example.dto;

import com.example.entity.Currency;

public record CurrencyResponseDto(long id, String code, String fullName, String sign) {
}
