package ru.ppxxd.deansoffice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ppxxd.deansoffice.model.Assessment;
import ru.ppxxd.deansoffice.service.AssessmentService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/assessments")
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentService assessmentService;

    @GetMapping
    public List<Assessment> findAll() {
        log.info("Получен запрос GET /assessments.");
        return assessmentService.getAll();
    }

    @GetMapping("/students/{id}")
    public List<Assessment> getAssessmentByStudentID(@PathVariable int id) {
        log.info("Получен запрос GET /assessment/students/{}.", id);
        return assessmentService.getAssessmentsByStudentID(id);
    }

    @PostMapping("/students/{id}")
    public Assessment add(@PathVariable int id, @Valid @RequestBody Assessment assessment) {
        log.info("Получен запрос POST /assessment/students/{}. Оценка у студента c id {} добавлена.", id, id);
        return assessmentService.addAssessment(id, assessment);
    }

    @PutMapping("/students/{id}")
    public Assessment update(@PathVariable int id, @Valid @RequestBody Assessment assessment) {
        log.info("Получен запрос PUT /assessment/students/{}. Оценка у студента c id {} обновлена.", id, id);
        return assessmentService.updateAssessment(id, assessment);
    }

    @DeleteMapping("/students/{id}")
    public Assessment delete(@PathVariable int id, @Valid @RequestBody Assessment assessment) {
        log.info("Получен запрос DELETE /assessment/students/{}. Оценка у студента c id {} удалена.", id, id);
        return assessmentService.deleteAssessment(id, assessment);
    }
}
