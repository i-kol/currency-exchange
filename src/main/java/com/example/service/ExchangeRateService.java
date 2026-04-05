package com.example.service;

import com.example.dao.ExchangeRateDao;
import com.example.dto.ExchangeRateDto;
import com.example.exception.DatabaseException;

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
                        exchangeRate.getId(), exchangeRate.getBaseCurrencyId(), exchangeRate.getTargetCurrencyId(), exchangeRate.getRate()
                )).collect(Collectors.toList());
    }

    public ExchangeRateDto findRate(int baseCurrencyId, int targetCurrencyId) {
        return exchangeRateDao.findExchangeRate(baseCurrencyId, targetCurrencyId).stream()
                .map(exchangeRate -> new ExchangeRateDto(
                        exchangeRate.getId(),
                        exchangeRate.getBaseCurrencyId(),
                        exchangeRate.getTargetCurrencyId(),
                        exchangeRate.getRate()))
                .findFirst()
                .orElseThrow(() -> new DatabaseException("Exchange rate with base code/target code: %d/%d not found"
                        .formatted(baseCurrencyId, targetCurrencyId)));
    }

    //TODO: надо реализовать оставшиеся методы по работе с курсами

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }
}
