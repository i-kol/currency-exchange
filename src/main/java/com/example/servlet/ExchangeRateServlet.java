package com.example.servlet;

import com.example.dto.ExchangeRateRequestDto;
import com.example.dto.ExchangeRateResponseDto;
import com.example.entity.ExchangeRate;
import com.example.exception.DatabaseException;
import com.example.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
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
    ObjectMapper mapper = new ObjectMapper();

    private static final class Pair {
        final String base;
        final String target;

        private Pair(String base, String target) {
            this.base = base;
            this.target = target;
        }
    }

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

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        //String methodName = req.getReader().toUpperCase();
        
        //надо считать имя метода
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        ExchangeRateRequestDto requestDto = mapper.readValue(req.getReader(), ExchangeRateRequestDto.class);
        ExchangeRateResponseDto responseDto = exchangeRateService.update(requestDto);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json; charset=UTF-8");

        mapper.writeValue(resp.getWriter(), responseDto);
    }

    private Pair parseCurrencyPair(String pathInfo) {
        if (pathInfo == null || pathInfo.isEmpty() || pathInfo.equals("/")) {
            throw new IllegalArgumentException("Path must have format: /EXCHANGERATE/XXXXXX");
        }
        //TODO: дописать парсер по ИИ
    }

    //TODO: надо сразу писать метод в соответствие с ТЗ: PATCH /exchangeRate/USDRUB,
    // т.о. мне нужно создать метод-парсер, который будет вычислять количество символов после слэш и дробить
    // его на первые 3 символа и последние 3 символа.
    // Также нужно определить для начала, что используемый метод именно PATCH и, если это так, то выполнять его дальше
}
