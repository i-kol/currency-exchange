package com.example.servlet;

import com.example.dto.CurrencyResponseDto;
import com.example.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet("/currency")
public class CurrencyServlet extends HttpServlet {
    private final CurrencyService currencyService = CurrencyService.getInstance();

    // http://localhost:8081/currency?code=USD

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String code = req.getParameter("code");

        Optional<CurrencyResponseDto> currency = currencyService.findByCode(code.trim().toUpperCase());

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write("<h1>Выбранная валюта:</h1>");
            printWriter.write("<ul>");


            if (currency.isPresent()) {
                CurrencyResponseDto currencyResponseDto = currency.get();
                printWriter.write("""
                            <br>%d. %s - %s - %s</br>
                        """.formatted(currencyResponseDto.getId(), currencyResponseDto.getCode(),
                        currencyResponseDto.getFullName(), currencyResponseDto.getSign()));

            } else {
                printWriter.write("<li>Валюта с кодом '" + code + "' не найдена.</li>");
            }

            printWriter.write("</ul>");
        }
    }
}