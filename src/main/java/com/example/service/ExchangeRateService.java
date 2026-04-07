package com.example.service;

import com.example.dao.ExchangeRateDao;
import com.example.dto.ExchangeRateRequestDto;
import com.example.dto.ExchangeRateResponseDto;
import com.example.entity.ExchangeRate;
import com.example.exception.DatabaseException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ExchangeRateService {
    private static final ExchangeRateService INSTANCE = new ExchangeRateService();

    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    private ExchangeRateService() {
    }

    public List<ExchangeRateResponseDto> findAll() {
        return exchangeRateDao.findAll().stream()
                .map(exchangeRate -> new ExchangeRateResponseDto(
                        exchangeRate.getId(), exchangeRate.getBaseCurrencyId(), exchangeRate.getTargetCurrencyId(), exchangeRate.getRate()
                )).collect(Collectors.toList());
    }

    public ExchangeRate findExchangeRate(int baseCurrencyId, int targetCurrencyId) {
        return exchangeRateDao.findExchangeRate(baseCurrencyId, targetCurrencyId).stream()
                .map(exchangeRate -> new ExchangeRate(
                        exchangeRate.getId(),
                        exchangeRate.getBaseCurrencyId(),
                        exchangeRate.getTargetCurrencyId(),
                        exchangeRate.getRate()))
                .findFirst()
                .orElseThrow(() -> new DatabaseException("Exchange rate with base code/target code: %d/%d not found"
                        .formatted(baseCurrencyId, targetCurrencyId)));
    }

    public ExchangeRateResponseDto add(ExchangeRateRequestDto requestDto) {
        int baseCurrencyId = requestDto.baseCurrencyId();
        int targetCurrencyId = requestDto.targetCurrencyId();
        BigDecimal rate = requestDto.rate();

        ExchangeRate exchangeRate = new ExchangeRate(baseCurrencyId, targetCurrencyId, rate);

        long id = exchangeRateDao.add(exchangeRate);

        return new ExchangeRateResponseDto(id, baseCurrencyId, targetCurrencyId, rate);
    }

    public ExchangeRateResponseDto update(ExchangeRateRequestDto requestDto) {
        int baseCurrencyId = requestDto.baseCurrencyId();
        int targetCurrencyId = requestDto.targetCurrencyId();
        BigDecimal rate = requestDto.rate();

        ExchangeRate foundRate = findExchangeRate(baseCurrencyId, targetCurrencyId);
        foundRate.setRate(rate);
        exchangeRateDao.update(foundRate);

        ExchangeRateResponseDto responseDto = new ExchangeRateResponseDto(foundRate.getId(),
                foundRate.getBaseCurrencyId(), foundRate.getTargetCurrencyId(), foundRate.getRate());

        return responseDto;
    }

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }
}
