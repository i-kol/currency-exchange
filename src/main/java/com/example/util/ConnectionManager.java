package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    public static Connection get() {
        try {
            Class.forName("org.sqlite.JDBC");
            String dbPath = ConnectionManager.class.getClassLoader().getResource("ce.db").getPath();
            return  DriverManager.getConnection("jdbc:sqlite:" + dbPath);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
