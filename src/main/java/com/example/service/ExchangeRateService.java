package com.example.service;

import com.example.dao.ExchangeRateDao;
import com.example.dto.ExchangeRateDto;

import java.util.List;
import java.util.stream.Collectors;

public class ExchangeRateService {
    private static final ExchangeRateService INSTANCE = new ExchangeRateService();

    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    private ExchangeRateService() {
    }

    public List<ExchangeRateDto> findAll() {
        return exchangeRateDao.findAll().stream()
                .map(exchangeRate -> new ExchangeRateDto(
                        exchangeRate.getId(), exchangeRate.getBaseCurrencyId(), exchangeRate.getBaseCurrencyId(), exchangeRate.getRate()
                )).collect(Collectors.toList());
    }

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }
}
