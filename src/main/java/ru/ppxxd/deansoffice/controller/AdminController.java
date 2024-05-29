package ru.ppxxd.deansoffice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ppxxd.deansoffice.model.User;
import ru.ppxxd.deansoffice.service.UserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    @PutMapping("/users/")
    public User update(@Valid @RequestBody User user) {
        log.info("Получен запрос PUT /admin/users. Пользователь c id {} обновлен.", user.getId());
        return userService.updateUser(user);
    }

    @DeleteMapping("/users/")
    public User delete(@Valid @RequestBody User user) {
        log.info("Получен запрос /admin/users. Пользователь c id {} удален.", user.getId());
        return userService.deleteUser(user);
    }

    @GetMapping("/users/")
    public List<User> findAll() {
        log.info("Получен запрос GET /admin/users.");
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        log.info("Получен запрос GET /admin/users/{}.", id);
        return userService.getUser(id);
    }
}
