package ru.ppxxd.deansoffice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ppxxd.deansoffice.exception.StudentNotFoundException;
import ru.ppxxd.deansoffice.model.Group;
import ru.ppxxd.deansoffice.model.Student;
import ru.ppxxd.deansoffice.model.User;
import ru.ppxxd.deansoffice.storage.StudentStorage;

import java.util.List;

@Service
public class StudentService {
    private final StudentStorage studentStorage;
    private final GroupService groupService;

    private final UserService userService;

    @Autowired
    public StudentService(@Qualifier("StudentStorage") StudentStorage studentStorage, GroupService groupService,
                          UserService userService) {
        this.studentStorage = studentStorage;
        this.groupService = groupService;
        this.userService = userService;
    }

    public Student createStudent(Student student) {
        User userCreatedForStudent = userService.createUser(userService.createUser(
                User.builder()
                        .id(null)
                        .login(student.getLastName().toLowerCase())
                        .password(student.getPhoneNumber().toString())
                        .role(User.Role.STUDENT)
                        .build()));
        student.setId(userCreatedForStudent.getId());
        student = studentStorage.addStudent(student);
        return student;
    }

    public Student updateStudent(Student student) {
        studentStorage.checkStudentExists(student.getId());
        return studentStorage.updateStudent(student);
    }

    public Student deleteStudent(Student student) {
        studentStorage.checkStudentExists(student.getId());
        return studentStorage.deleteStudent(student);
    }

    public Student getStudent(Integer id) {
        studentStorage.checkStudentExists(id);
        return studentStorage.getStudentByID(id);
    }

    public List<Student> getAll() {
        return studentStorage.getStudents();
    }


    public Group getStudentGroup(int studentID) {
        studentStorage.checkStudentExists(studentID);
        return groupService.getGroupByStudentID(studentID);
    }

    public Group addStudentGroup(int studentID, Group group) {
        studentStorage.checkStudentExists(studentID);
        return groupService.addStudentToGroup(studentID, group);
    }

    public Group updateStudentGroup(int studentID, Group group) {
        studentStorage.checkStudentExists(studentID);
        return groupService.updateStudentGroup(studentID, group);
    }

    public Group deleteStudentGroup(int studentID, Group group) {
        studentStorage.checkStudentExists(studentID);
        return groupService.deleteStudentFromGroup(studentID, group);
    }
}

