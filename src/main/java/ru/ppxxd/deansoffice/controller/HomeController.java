package ru.ppxxd.deansoffice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return
                "<html>\n" +
                        "  <head>\n" +
                        "    <title>Home</title>\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <p>\n" +
                        "      <a href=\"http://localhost:8080/students\">Студенты</a>\n" +
                        "    </p>\n" +
                        "    <p>\n" +
                        "      <a href=\"http://localhost:8080/teachers\">Преподаватели</a>\n" +
                        "    </p>\n" +
                        "    <p>\n" +
                        "      <a href=\"http://localhost:8080/admin/users/\">Админы</a>\n" +
                        "    </p>\n" +
                        "    <p>\n" +
                        "      <a href=\"http://localhost:8080/logout\">Выйти</a>\n" +
                        "    </p>\n" +
                        "  </body>\n" +
                        "</html>\n";
    }
}