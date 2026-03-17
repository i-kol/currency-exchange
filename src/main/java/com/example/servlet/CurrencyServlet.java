package com.example.servlet;

import com.example.dto.CurrencyRequestDto;
import com.example.dto.CurrencyResponseDto;
import com.example.service.CurrencyService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet("/currencies")
public class CurrencyServlet extends HttpServlet {
    private final CurrencyService currencyService = CurrencyService.getInstance();
    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

//        String code = req.getParameter("code");
//        String fullName = req.getParameter("fullName");
//        String sign = req.getParameter("sign");

//        Optional<CurrencyResponseDto> currency = currencyService.findByCode(code.trim().toUpperCase());
//        CurrencyResponseDto currency = currencyService.save(new CurrencyRequestDto(code, fullName, sign));

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write("<h1>Список валют</h1>");
            printWriter.write("<ul>");

            printWriter.write("Валюта добавлена, проверяйте в базе сами");

//            currencyService.findAll().forEach(currencyDto -> {
//                printWriter.write("""
//                        <li>
//                            <a href="/exchange?id=%d">%s</a>
//                        </li>
//                        """.formatted(currencyDto.getId(), currencyDto.getFullName()));
//            });


//            http://localhost:8081/currencies?code=USD
//            if (currency.isPresent()) {
//                CurrencyResponseDto currencyResponseDto = currency.get();
//                printWriter.write("""
//                        <li>
//                            <br>%d - %s</br>
//                        </li>
//                        """.formatted(currencyResponseDto.getId(), currencyResponseDto.getFullName()));
//
//            } else {
//                printWriter.write("<li>Валюта с кодом '" + code + "' не найдена.</li>");
//            }

            printWriter.write("</ul>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String fullName = req.getParameter("fullName");
        String sign = req.getParameter("sign");

        CurrencyRequestDto currencyRequestDto = new CurrencyRequestDto(code, fullName, sign);
        CurrencyResponseDto responseDto = currencyService.save(currencyRequestDto);

        //Настроить взаимодействие с JSON и REST
    }
}