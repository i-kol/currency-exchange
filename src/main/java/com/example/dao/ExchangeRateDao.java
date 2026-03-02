package com.example.dao;

import com.example.entity.ExchangeRate;
import com.example.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDao implements Dao<Integer, ExchangeRate> {
    private static final ExchangeRateDao INSTANCE = new ExchangeRateDao();
    private static final String FIND_ALL = "SELECT ID, BaseCurrencyId, TargetCurrencyId, Rate FROM ExchangeRates";

    private ExchangeRateDao() {
    }

    public static ExchangeRateDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ExchangeRate> findAll() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                exchangeRates.add(buildExchangeRates(resultSet));
            }

            return exchangeRates;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<ExchangeRate> findByCurrencyIds(Integer id) {
        return Optional.empty();
    }

    public void update(ExchangeRate entity) {
    }

    @Override
    public ExchangeRate save(ExchangeRate entity) {
        return null;
    }

    private ExchangeRate buildExchangeRates(ResultSet resultSet) throws SQLException {
        return new ExchangeRate(
                resultSet.getObject("id", Long.class),
                resultSet.getObject("baseCurrencyId", Integer.class),
                resultSet.getObject("targetCurrencyId", Integer.class),
                resultSet.getObject("rate", BigDecimal.class)
        );
    }
}
