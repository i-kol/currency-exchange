package com.example.servlet;

import com.example.service.ExchangeRateService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/exchange-rates")
public class ExchangeRateServlet extends HttpServlet {
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
                        """.formatted(exchangeRateDto.getId(), exchangeRateDto.getRate()));
            });

            printWriter.write("</ul");
        }
    }
}
