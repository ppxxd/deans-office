package ru.ppxxd.deansoffice.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ppxxd.deansoffice.exception.StudentNotFoundException;
import ru.ppxxd.deansoffice.exception.TeacherNotFoundException;
import ru.ppxxd.deansoffice.model.Education;
import ru.ppxxd.deansoffice.model.Subject;
import ru.ppxxd.deansoffice.model.Teacher;
import ru.ppxxd.deansoffice.model.WorkExperience;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component("TeacherStorage")
@RequiredArgsConstructor
@Slf4j
public class TeacherStorage {
    private final JdbcTemplate jdbcTemplate;

    public List<Teacher> getTeachers() {
        String sqlQuery = "SELECT * FROM TEACHERS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToTeacher);
    }

    public Teacher addTeacher(Teacher teacher) {
        String sqlQuery = "INSERT INTO TEACHERS (id, chair_id, position_id, last_name, name, patronymic, phone_number)" +
                " VALUES (?, ?,?,?,?,?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, teacher.getId());
            ps.setInt(2, teacher.getChairID());
            ps.setInt(3, teacher.getPositionID());
            ps.setString(4, teacher.getLastName());
            ps.setString(5, teacher.getName());
            ps.setString(6, teacher.getPatronymic());
            ps.setInt(7, teacher.getPhoneNumber());
            return ps;
        });

        return teacher;
    }

    public boolean checkTeacherExist(Integer id) {
        String sqlQuery = "SELECT id FROM TEACHERS WHERE id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!rowSet.next()) {
            throw new TeacherNotFoundException(String.format("Учитель с id %d не найден.", id));
        }
        return true;
    }

    public Teacher updateTeacher(Teacher teacher) {
        String sqlQuery = "UPDATE TEACHERS SET " +
                "CHAIR_ID = ?," +
                "POSITION_ID = ?," +
                "LAST_NAME = ?," +
                "NAME = ?," +
                "PATRONYMIC = ?," +
                "PHONE_NUMBER = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sqlQuery, teacher.getChairID(), teacher.getPositionID(), teacher.getLastName(),
                teacher.getName(), teacher.getPatronymic(), teacher.getPhoneNumber(), teacher.getId());
        return teacher;
    }

    public Teacher deleteTeacher(Teacher teacher) {
        String sql = "DELETE FROM teachers WHERE id = ?";
        jdbcTemplate.update(sql, teacher.getId());
        return teacher;
    }

    public Teacher getTeacherByID(Integer id) {
        String sqlQuery = "SELECT * FROM teachers WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToTeacher, id);
    }

    private Teacher mapRowToTeacher(ResultSet rs, int rowNum) throws SQLException {
        return Teacher.builder()
                .id(rs.getInt("id"))
                .chairID(rs.getInt("chair_id"))
                .positionID(rs.getInt("position_id"))
                .lastName(rs.getString("last_name"))
                .name(rs.getString("name"))
                .patronymic(rs.getString("patronymic"))
                .build();
    }
}
