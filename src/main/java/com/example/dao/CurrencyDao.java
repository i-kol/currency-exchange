package com.example.dao;

import com.example.util.ConnectionManager;
import com.example.entity.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao implements Dao<Integer, Currency> {
    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private static final String FIND_ALL_SQL = "SELECT ID, Code, FullName, Sign FROM Currencies";
    private static final String FIND_BY_CODE_SQL = FIND_ALL_SQL + " WHERE Code = ?";
    private static final String ADD_CURRENCY_SQL = "INSERT INTO Currencies (Code, FullName, Sign) VALUES (?, ?, ?)";

    private CurrencyDao() {
    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                currencies.add(getCurrency(resultSet));
            }
            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e); //TODO: создать DataAccessException "Failed to get currency"
        }
    }

    public Optional<Currency> findByCode(String code) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CODE_SQL)) {

            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getCurrency(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);  //TODO: сделать релевантные исключения "Валюта с таким code не найдена"
        }
    }

    @Override
    public Currency add(Currency currency) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_CURRENCY_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getFullName());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Currency creation failed, id not received!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currency;
    }

    private Currency getCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getLong("id"),
                resultSet.getString("code"),
                resultSet.getString("fullName"),
                resultSet.getString("sign")
        );
    }
}
