package ru.ppxxd.deansoffice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ppxxd.deansoffice.exception.TeacherNotFoundException;
import ru.ppxxd.deansoffice.model.Education;
import ru.ppxxd.deansoffice.model.Teacher;
import ru.ppxxd.deansoffice.model.User;
import ru.ppxxd.deansoffice.model.WorkExperience;
import ru.ppxxd.deansoffice.storage.EducationStorage;
import ru.ppxxd.deansoffice.storage.TeacherStorage;
import ru.ppxxd.deansoffice.storage.WorkExperienceStorage;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherStorage teacherStorage;
    private final EducationStorage educationStorage;
    private final WorkExperienceStorage workExperienceStorage;
    private final UserService userService;

    @Autowired
    public TeacherService(TeacherStorage teacherStorage, EducationStorage educationStorage,
                          WorkExperienceStorage workExperienceStorage, UserService userService) {
        this.teacherStorage = teacherStorage;
        this.educationStorage = educationStorage;
        this.workExperienceStorage = workExperienceStorage;
        this.userService = userService;
    }
    public Teacher addTeacher(Teacher teacher) {
        User userCreatedForTeacher = userService.createUser(
                User.builder()
                        .id(null)
                        .login(teacher.getLastName().toLowerCase())
                        .password(teacher.getPhoneNumber().toString())
                        .role(User.Role.TEACHER)
                        .build());
        teacher.setId(userCreatedForTeacher.getId());
        teacher = teacherStorage.addTeacher(teacher);
        return teacher;
    }

    public Teacher updateTeacher(Teacher teacher) {
        if (!teacherStorage.checkTeacherExist(teacher.getId())) {
            throw new TeacherNotFoundException("Преподаватель с таким id не существует.");
        }
        return teacherStorage.updateTeacher(teacher);
    }

    public Teacher deleteTeacher(Teacher teacher) {
        if (!teacherStorage.checkTeacherExist(teacher.getId())) {
            throw new TeacherNotFoundException("Преподаватель с таким id не существует.");
        }
        return teacherStorage.deleteTeacher(teacher);
    }

    public Teacher getTeacherByID(Integer id) {
        if (!teacherStorage.checkTeacherExist(id)) {
            throw new TeacherNotFoundException("Преподаватель с таким id не существует.");
        }
        return teacherStorage.getTeacherByID(id);
    }

    public List<Teacher> getTeachers() {
        return teacherStorage.getTeachers();
    }

    public Education getTeacherEducation(Integer id) {
        teacherStorage.checkTeacherExist(id);
        Teacher teacher = teacherStorage.getTeacherByID(id);
        return educationStorage.getEducationByTeacherID(id);
    }

    public WorkExperience getTeacherExperience(Integer id) {
        teacherStorage.checkTeacherExist(id);
        return workExperienceStorage.getEducationByTeacherID(id);
    }

    public Education addTeacherEducation(int id, Education education) {
        teacherStorage.checkTeacherExist(id);
        return educationStorage.addTeacherEducation(id, education);
    }

    public Education updateTeacherEducation(int id, Education education) {
        teacherStorage.checkTeacherExist(id);
        return educationStorage.updateTeacherEducation(id, education);
    }

    public Education deleteTeacherEducation(int id, Education education) {
        teacherStorage.checkTeacherExist(id);
        return educationStorage.deleteTeacherEducation(id, education);
    }

    public WorkExperience addTeacherExperience(int id, WorkExperience experience) {
        teacherStorage.checkTeacherExist(id);
        return workExperienceStorage.addTeacherWorkExperience(id, experience);
    }

    public WorkExperience updateTeacherExperience(int id, WorkExperience experience) {
        teacherStorage.checkTeacherExist(id);
        return workExperienceStorage.updateTeacherWorkExperience(id, experience);
    }

    public WorkExperience deleteTeacherExperience(int id, WorkExperience experience) {
        teacherStorage.checkTeacherExist(id);
        return workExperienceStorage.deleteTeacherWorkExperience(id, experience);
    }
}
