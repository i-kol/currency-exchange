package com.example.servlet;

import com.example.dto.ExchangeRateResponseDto;
import com.example.entity.ExchangeRate;
import com.example.exception.DatabaseException;
import com.example.service.ExchangeRateService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/exchangeRate")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8);

        int baseCurrencyId = Integer.parseInt(req.getParameter("baseCurrencyId"));
        int targetCurrencyId = Integer.parseInt(req.getParameter("targetCurrencyId"));

        try {
            ExchangeRate exchangeRate = exchangeRateService.findExchangeRate(baseCurrencyId, targetCurrencyId);

            ExchangeRateResponseDto responseDto = new ExchangeRateResponseDto(
                    exchangeRate.getId(), exchangeRate.getBaseCurrencyId(),
                    exchangeRate.getTargetCurrencyId(), exchangeRate.getRate());

            try (PrintWriter writer = resp.getWriter()) {
                writer.write("""
                    <h1>Поиск курса валюты</h1>
                    <p>Курс валюты %d к валюте %d = %s</p>
                    """.formatted(responseDto.baseCurrencyId(), responseDto.targetCurrencyId(),
                        responseDto.rate().toPlainString()));
            }
        } catch (DatabaseException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    //TODO: сюда добавляю метод findExchangeRate и update
}
