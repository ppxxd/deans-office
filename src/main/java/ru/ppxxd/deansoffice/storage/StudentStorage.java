package ru.ppxxd.deansoffice.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ppxxd.deansoffice.exception.StudentNotFoundException;
import ru.ppxxd.deansoffice.model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component("StudentStorage")
@RequiredArgsConstructor
@Slf4j
public class StudentStorage {
    private final JdbcTemplate jdbcTemplate;

    public Student getStudentByID(Integer id) {
        String sqlQuery = "SELECT * FROM students WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToStudent, id);
    }

    public boolean checkStudentExists(Integer id) {
        String sqlQuery = "SELECT id FROM students WHERE id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!rowSet.next()) {
            throw new StudentNotFoundException(String.format("Студент с id %d не найден.", id));
        }
        return true;
    }

    public Student addStudent(Student student) {
        String sqlQuery = "INSERT INTO students (ID, LAST_NAME, NAME, PATRONYMIC, PHONE_NUMBER) VALUES (?, ?,?,?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, student.getId());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getName());
            ps.setString(4, student.getPatronymic());
            ps.setInt(5, student.getPhoneNumber());
            return ps;
        });
        return student;
    }

    public Student updateStudent(Student student) {
        String sqlQuery = "UPDATE students SET " +
                "LAST_NAME = ?," +
                "NAME = ?," +
                "PATRONYMIC = ?," +
                "PHONE_NUMBER = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sqlQuery, student.getGroupID(), student.getLastName(), student.getName(),
                student.getPatronymic(), student.getPhoneNumber(), student.getId());
        return student;
    }

    public Student deleteStudent(Student student) {
        String sql = "DELETE FROM students WHERE id = ?";
        jdbcTemplate.update(sql, student.getId());
        return student;
    }

    public List<Student> getStudents() {
        String sqlQuery = "SELECT * FROM students";
        return jdbcTemplate.query(sqlQuery, this::mapRowToStudent);
    }

    private Student mapRowToStudent(ResultSet rs, int rowNum) throws SQLException {
        return Student.builder()
                .id(rs.getInt("id"))
                .groupID(rs.getInt("group_id"))
                .lastName(rs.getString("last_name"))
                .name(rs.getString("name"))
                .patronymic(rs.getString("patronymic"))
                .phoneNumber(rs.getInt("phone_number"))
                .build();
    }
}
