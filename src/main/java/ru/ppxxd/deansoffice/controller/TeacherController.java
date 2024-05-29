package ru.ppxxd.deansoffice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ppxxd.deansoffice.model.Education;
import ru.ppxxd.deansoffice.model.Group;
import ru.ppxxd.deansoffice.model.Teacher;
import ru.ppxxd.deansoffice.model.WorkExperience;
import ru.ppxxd.deansoffice.service.TeacherService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public Teacher add(@Valid @RequestBody Teacher teacher) {
        log.info("Получен запрос POST /teachers. Преподаватель добавлен.");
        return teacherService.addTeacher(teacher);
    }

    @PutMapping
    public Teacher update(@Valid @RequestBody Teacher teacher) {
        log.info("Получен запрос PUT /teachers. Преподаватель c id {} обновлен.", teacher.getId());
        return teacherService.updateTeacher(teacher);
    }

    @DeleteMapping
    public Teacher delete(@Valid @RequestBody Teacher teacher) {
        log.info("Получен запрос DELETE /teachers. Преподаватель c id {} удален.", teacher.getId());
        return teacherService.deleteTeacher(teacher);
    }

    @GetMapping
    public List<Teacher> findAll() {
        log.info("Получен запрос GET /teachers.");
        return teacherService.getTeachers();
    }

    @GetMapping("/find/{id}")
    public Teacher getTeacher(@PathVariable int id) {
        log.info("Получен запрос GET /teachers/{}.", id);
        return teacherService.getTeacherByID(id);
    }

    @GetMapping("/{id}/education")
    public Education getTeacherEducation(@PathVariable int id) {
        log.info("Получен запрос GET /teachers/{}/education", id);
        return teacherService.getTeacherEducation(id);
    }

    @PostMapping("/{id}/education")
    public Education addTeacherEducation(@PathVariable int id, @Valid @RequestBody Education education) {
        log.info("Получен запрос POST /teachers/{}/education", id);
        return teacherService.addTeacherEducation(id, education);
    }

    @PutMapping("/{id}/education")
    public Education updateTeacherEducation(@PathVariable int id, @Valid @RequestBody Education education) {
        log.info("Получен запрос PUT /teachers/{}/education", id);
        return teacherService.updateTeacherEducation(id, education);
    }

    @DeleteMapping("/{id}/education")
    public Education deleteTeacherEducation(@PathVariable int id, @Valid @RequestBody Education education) {
        log.info("Получен запрос PUT /teachers/{}/education", id);
        return teacherService.deleteTeacherEducation(id, education);
    }

    @GetMapping("/{id}/experience")
    public WorkExperience getTeacherExperience(@PathVariable int id) {
        log.info("Получен запрос GET /teachers/{}/experience", id);
        return teacherService.getTeacherExperience(id);
    }

    @PostMapping("/{id}/experience")
    public WorkExperience addTeacherExperience(@PathVariable int id, @Valid @RequestBody WorkExperience experience) {
        log.info("Получен запрос POST /teachers/{}/experience", id);
        return teacherService.addTeacherExperience(id, experience);
    }

    @PutMapping("/{id}/experience")
    public WorkExperience updateTeacherExperience(@PathVariable int id, @Valid @RequestBody WorkExperience experience) {
        log.info("Получен запрос PUT /teachers/{}/experience", id);
        return teacherService.updateTeacherExperience(id, experience);
    }

    @DeleteMapping("/{id}/experience")
    public WorkExperience deleteTeacherExperience(@PathVariable int id, @Valid @RequestBody WorkExperience experience) {
        log.info("Получен запрос DELETE /teachers/{}/experience", id);
        return teacherService.deleteTeacherExperience(id, experience);
    }
}
