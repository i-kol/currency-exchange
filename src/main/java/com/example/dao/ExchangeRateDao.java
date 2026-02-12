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
    private static final String FIND_ALL_BY_EXCHANGE_RATE_ID = "SELECT ID, BaseCurrencyId, TargetCurrencyId, Rate FROM ExchangeRates WHERE ID=?";

    private ExchangeRateDao() {
    }

    public static ExchangeRateDao getInstance() {
        return INSTANCE;
    }

    public List<ExchangeRate> findAllByExchangeRateId(int id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_EXCHANGE_RATE_ID)) {

            preparedStatement.setObject(1, "id");

            ResultSet resultSet = preparedStatement.executeQuery();

            List<ExchangeRate> exchangeRates = new ArrayList<>();

            while (resultSet.next()) {
                exchangeRates.add(buildExchangeRates(resultSet));
            }

            return exchangeRates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ExchangeRate> findAll() {
        return List.of();
    }

    @Override
    public Optional<ExchangeRate> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public void update(ExchangeRate entity) {

    }

    @Override
    public ExchangeRate save(ExchangeRate entity) {
        return null;
    }

    private ExchangeRate buildExchangeRates(ResultSet resultSet) throws SQLException {
        return new ExchangeRate(
                resultSet.getObject("id", Integer.class),
                resultSet.getObject("baseCurrencyId", Integer.class),
                resultSet.getObject("targetCurrencyId", Integer.class),
                resultSet.getObject("rate", BigDecimal.class)
        );
    }
}
