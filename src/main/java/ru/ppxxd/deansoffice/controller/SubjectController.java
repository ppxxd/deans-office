package ru.ppxxd.deansoffice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ppxxd.deansoffice.model.Subject;
import ru.ppxxd.deansoffice.service.SubjectService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;
    @PostMapping
    public Subject add(@Valid @RequestBody Subject subject) {
        log.info("Получен запрос POST /subjects. Предмет добавлен.");
        return subjectService.addSubject(subject);
    }

    @PutMapping
    public Subject update(@Valid @RequestBody Subject subject) {
        log.info("Получен запрос PUT /subjects. Предмет c id {} обновлен.", subject.getSubjectID());
        return subjectService.updateSubject(subject);
    }

    @DeleteMapping
    public Subject delete(@Valid @RequestBody Subject subject) {
        log.info("Получен запрос DELETE /subjects. Предмет c id {} удален.", subject.getSubjectID());
        return subjectService.deleteSubject(subject);
    }

    @GetMapping
    public List<Subject> findAll() {
        log.info("Получен запрос GET /subjects.");
        return subjectService.getAll();
    }

    @GetMapping("/{id}")
    public Subject getSubject(@PathVariable int id) {
        log.info("Получен запрос GET /subjects/{}.", id);
        return subjectService.getSubjectByID(id);
    }
}
