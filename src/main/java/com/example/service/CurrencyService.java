package com.example.service;

import com.example.dao.CurrencyDao;
import com.example.dto.CurrencyRequestDto;
import com.example.dto.CurrencyResponseDto;
import com.example.entity.Currency;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CurrencyService {
    private static final CurrencyService INSTANCE = new CurrencyService();

    private final CurrencyDao currencyDao = CurrencyDao.getInstance();

    private CurrencyService() {
    }

    public List<CurrencyResponseDto> findAll() {
        return currencyDao.findAll().stream()
                .map(currency -> new CurrencyResponseDto(
                        currency.getId(), currency.getCode(), currency.getFullName(), currency.getSign()
                )).collect(Collectors.toList());
    }

    public Optional<CurrencyResponseDto> findByCode(String code) {
        return currencyDao.findByCode(code)
                .map(currency -> new CurrencyResponseDto(
                        currency.getId(), currency.getCode(), currency.getFullName(), currency.getSign()
                ));
    }

    public CurrencyResponseDto save(CurrencyRequestDto currencyRequestDto) {

        String code = currencyRequestDto.getCode();
        String fullName = currencyRequestDto.getFullName();
        String sign = currencyRequestDto.getSign();

        Currency currency = new Currency(code, fullName, sign);

        long id = currencyDao.save(currency);

        return new CurrencyResponseDto(id, code, fullName, sign);
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }
}
