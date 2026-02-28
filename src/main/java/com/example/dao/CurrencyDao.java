package com.example.dao;

import com.example.util.ConnectionManager;
import com.example.entity.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao implements Dao<Integer, Currency> {
    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private static final String FIND_ALL = "SELECT ID, Code, FullName, Sign FROM Currencies";

    private CurrencyDao() {
    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                currencies.add(buildCurrency(resultSet));
            }

            return currencies;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Currency> findByCode(Integer id) {
        return Optional.empty();
    }

    @Override
    public Currency save(Currency entity) {
        return null;
    }

    private Currency buildCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getObject("id", Long.class),
                resultSet.getObject("code", String.class),
                resultSet.getObject("fullName", String.class),
                resultSet.getObject("sign", String.class)
        );
    }
}
