package com.example.dto;

public record CurrencyRequestDto(String code, String fullName, String sign) {

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
