package ru.ppxxd.deansoffice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ppxxd.deansoffice.model.Group;
import ru.ppxxd.deansoffice.model.Student;
import ru.ppxxd.deansoffice.service.StudentService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public Student add(@Valid @RequestBody Student student) {
        log.info("Получен запрос POST /students. Студент добавлен.");
        return studentService.createStudent(student);
    }

    @PutMapping
    public Student update(@Valid @RequestBody Student student) {
        log.info("Получен запрос PUT /students. Студент c id {} обновлен.", student.getId());
        return studentService.updateStudent(student);
    }

    @DeleteMapping
    public Student delete(@Valid @RequestBody Student student) {
        log.info("Получен запрос DELETE /students. Студент c id {} удален.", student.getId());
        return studentService.deleteStudent(student);
    }

    @GetMapping
    public List<Student> findAll() {
        log.info("Получен запрос GET /students.");
        return studentService.getAll();
    }

    @GetMapping("/find/{id}")
    public Student getStudent(@PathVariable int id) {
        log.info("Получен запрос GET /students/{}.", id);
        return studentService.getStudent(id);
    }

    @GetMapping("/{id}/group")
    public Group getStudentGroup(@PathVariable int id) {
        log.info("Получен запрос GET /students/{id}/group. Группа у студента c id {} запрошена.", id);
        return studentService.getStudentGroup(id);
    }

    @PostMapping("/{id}/group")
    public Group addStudentGroup(@PathVariable int id, @Valid @RequestBody Group group) {
        log.info("Получен запрос POST /students/{id}/group. Группа у студента c id {} добавлена.", id);
        return studentService.addStudentGroup(id, group);
    }

    @PutMapping("/{id}/group")
    public Group updateStudentGroup(@PathVariable int id, @Valid @RequestBody Group group) {
        log.info("Получен запрос PUT /students/{id}/group. Группа у студента c id {} обновлена.", id);
        return studentService.updateStudentGroup(id, group);
    }

    @DeleteMapping("{id}/group")
    public Group deleteStudentGroup(@PathVariable int id, @Valid @RequestBody Group group) {
        log.info("Получен запрос DELETE /students/{id}/group. Группа у студента c id {} удалена.", id);
        return studentService.deleteStudentGroup(id, group);
    }
}
