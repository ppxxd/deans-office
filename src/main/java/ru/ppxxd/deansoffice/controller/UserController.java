package ru.ppxxd.deansoffice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ppxxd.deansoffice.model.Student;
import ru.ppxxd.deansoffice.model.User;
import ru.ppxxd.deansoffice.service.StudentService;
import ru.ppxxd.deansoffice.service.UserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public User add(@Valid @RequestBody User user) {
        log.info("Получен запрос POST /users. Пользователь добавлен.");
        return userService.createUser(user);
    }

    @GetMapping("/login")
    public boolean loginUser(@RequestParam String login, @RequestParam String password) {
        return userService.loginUser(login, password);
    }
}
