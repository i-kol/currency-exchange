package com.example.service;

import com.example.dao.CurrencyDao;
import com.example.dto.CurrencyDto;

import java.util.List;
import java.util.stream.Collectors;

public class CurrencyService {
    private static final CurrencyService INSTANCE = new CurrencyService();

    private final CurrencyDao currencyDao = CurrencyDao.getInstance();

    private CurrencyService() {
    }

    public List<CurrencyDto> findAll() {
        return currencyDao.findAll().stream()
                .map(currency -> new CurrencyDto(
                        currency.getId(), currency.getCode(), currency.getFullName(), currency.getSign()
                )).collect(Collectors.toList());
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }
}
