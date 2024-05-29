package ru.ppxxd.deansoffice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ppxxd.deansoffice.model.Position;
import ru.ppxxd.deansoffice.service.PositionService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {
    private final PositionService positionService;

    @GetMapping("/{id}")
    public Position getPosition(@PathVariable int id) {
        log.info("Получен запрос GET /positions/{}.", id);
        return positionService.getPosition(id);
    }

    @GetMapping
    public List<Position> findAll() {
        log.info("Получен запрос GET /positions.");
        return positionService.getAll();
    }
}
