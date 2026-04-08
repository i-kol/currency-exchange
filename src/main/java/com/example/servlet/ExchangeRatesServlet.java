package com.example.servlet;

import com.example.dto.ExchangeRateRequestDto;
import com.example.dto.ExchangeRateResponseDto;
import com.example.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write("<h1>Курсы валют</h1>");
            printWriter.write("<ul>");

            exchangeRateService.findAll().forEach(exchangeRateDto -> {
                printWriter.write("""
                        <li>
                            <a href="/exchange?id=%d">%f</a>
                        </li>
                        """.formatted(exchangeRateDto.id(), exchangeRateDto.rate()));
            });

            printWriter.write("</ul");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        ExchangeRateRequestDto requestDto = mapper.readValue(req.getReader(), ExchangeRateRequestDto.class);

        ExchangeRateResponseDto responseDto = exchangeRateService.add(requestDto);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json; charset=UTF-8");

        mapper.writeValue(resp.getWriter(), responseDto);
    }
}
