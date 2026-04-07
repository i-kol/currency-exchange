package com.example.servlet;

import com.example.dto.CurrencyRequestDto;
import com.example.dto.CurrencyResponseDto;
import com.example.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write("<h1>Список валют</h1>");
            printWriter.write("<ul>");

            currencyService.findAll().forEach(currencyDto -> {
                printWriter.write("""
                        <li>
                            <a href="/exchange?id=%d">%d. %s - %s - %s</a>
                        </li>
                        """.formatted(currencyDto.id(), currencyDto.id(), currencyDto.code(),
                        currencyDto.fullName(), currencyDto.sign()));
            });

            printWriter.write("</ul>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Устанавливаем кодировку
        req.setCharacterEncoding("UTF-8");

        // Читаем JSON из тела запроса
        ObjectMapper mapper = new ObjectMapper();
        CurrencyRequestDto requestDto = mapper.readValue(req.getReader(), CurrencyRequestDto.class);

        // Сохраняем через сервис
        CurrencyResponseDto responseDto = currencyService.add(requestDto);

        // Отправляем ответ как JSON с кодом 201
        resp.setStatus(HttpServletResponse.SC_CREATED);
        //Говорит браузеру или клиенту: "Тело ответа — это JSON, и оно закодировано в UTF-8"
        resp.setContentType("application/json; charset=UTF-8");
        mapper.writeValue(resp.getWriter(), responseDto);
    }
}