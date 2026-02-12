package com.example.dto;

public record CurrencyDto(int id, String code, String fullName, String sign) {
    public int getId() {
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
