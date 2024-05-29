package ru.ppxxd.deansoffice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ppxxd.deansoffice.model.Chair;
import ru.ppxxd.deansoffice.service.ChairService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chairs")
public class ChairController {
    private final ChairService chairService;

    @GetMapping
    public List<Chair> findAll() {
        log.info("Получен запрос GET /chairs.");
        return chairService.getAll();
    }

    @PostMapping
    public Chair add(@Valid @RequestBody Chair chair) {
        log.info("Получен запрос POST /chairs. Кафедра добавлена.");
        return chairService.addChair(chair);
    }

    @PutMapping
    public Chair update(@Valid @RequestBody Chair chair) {
        log.info("Получен запрос PUT /chairs. Кафедра c id {} обновлена.", chair.getChairID());
        return chairService.updateChair(chair);
    }

    @DeleteMapping
    public Chair delete(@Valid @RequestBody Chair chair) {
        log.info("Получен запрос DELETE /chairs. Кафедра c id {} удалена.", chair.getChairID());
        return chairService.deleteChair(chair);
    }

    @GetMapping("/find/{id}")
    public Chair getChair(@PathVariable int id) {
        log.info("Получен запрос GET /chairs/{}.", id);
        return chairService.getChairByID(id);
    }
}
