package com.example.dto;

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
}
