package com.example.dto;

import com.example.entity.Currency;

public record CurrencyResponseDto(long id, String code, String fullName, String sign) {

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSign() {
        return sign;
    }

    // Статический метод для преобразования из сущности (если понадобится)
    public static CurrencyResponseDto from(Currency currency) {
        return new CurrencyResponseDto(currency.getId(), currency.getCode(), currency.getFullName(), currency.getSign());
    }
}
