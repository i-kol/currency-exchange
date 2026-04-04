package com.example.dao;

import com.example.entity.ExchangeRate;
import com.example.exception.DatabaseException;
import com.example.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDao implements Dao<Integer, ExchangeRate> {
    private static final ExchangeRateDao INSTANCE = new ExchangeRateDao();
    private static final String FIND_ALL_SQL = "SELECT ID, BaseCurrencyId, TargetCurrencyId, Rate FROM ExchangeRates";
    private static final String FIND_BY_CURRENCY_IDS_SQL = "SELECT Rate FROM ExchangeRates WHERE BaseCurrencyId = ? AND TargetCurrencyId = ?";
    private static final String ADD_EXCHANGE_RATE_SQL = "INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (?, ?, ?)";
    private static final String UPDATE_EXCHANGE_RATE_SQL = "UPDATE ExchangeRates SET Rate = ? WHERE ID = ? RETURNING Rate";

    private ExchangeRateDao() {
    }

    public static ExchangeRateDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ExchangeRate> findAll() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                exchangeRates.add(getExchangeRates(resultSet));
            }

            return exchangeRates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<ExchangeRate> findByCurrencyIds(int baseCurrencyId, int targetCurrencyId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CURRENCY_IDS_SQL)) {

            statement.setInt(1, baseCurrencyId);
            statement.setInt(2, targetCurrencyId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getExchangeRates(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get exchange rate of id's: %d-%d".formatted(baseCurrencyId, targetCurrencyId));
        }
    }

    @Override
    public Long add(ExchangeRate exchangeRate) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_EXCHANGE_RATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, exchangeRate.getBaseCurrencyId());
            statement.setInt(2, exchangeRate.getTargetCurrencyId());
            statement.setBigDecimal(3, exchangeRate.getRate());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new DatabaseException("Adding exchange rate failed, ID not received!");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to add exchange rate");
        }
    }

    public void update(ExchangeRate exchangeRate) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_EXCHANGE_RATE_SQL)) {

            statement.setBigDecimal(1, exchangeRate.getRate());
            statement.setLong(2, exchangeRate.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Failed to update exchange rate");
        }
    }

    private ExchangeRate getExchangeRates(ResultSet resultSet) throws SQLException {
        return new ExchangeRate(
                resultSet.getLong("id"),
                resultSet.getInt("baseCurrencyId"),
                resultSet.getInt("targetCurrencyId"),
                resultSet.getBigDecimal("rate")
        );
    }
}
