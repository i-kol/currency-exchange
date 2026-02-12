package com.example;

import com.example.util.ConnectionManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/qwe")
public class HelloServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        ConnectionManager connectionManager = new ConnectionManager();

        try(Statement statement = connectionManager.get().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Code from Currencies")) {

            while (resultSet.next()) {
                printWriter.println(resultSet.getString("Code"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
