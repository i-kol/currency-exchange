package com.example.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final HikariDataSource dataSource;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC driver not found.");
        }

        HikariConfig config = new HikariConfig();

        // В IDEA: Run → Edit Configurations → VM Options. Добавить: -Dproject.root=D:/IdeaProjects/currency-exchange
        // Получаем путь к папке проекта и далее относительный путь к БД
        String projectRoot = System.getProperty("project.root", ".");
        String dbPath = projectRoot + "/data/ce.db";

        config.setJdbcUrl("jdbc:sqlite:" + dbPath);
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(10);

        dataSource = new HikariDataSource(config);

        System.out.println("[DB] Using database at: " + dbPath);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
