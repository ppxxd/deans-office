package ru.ppxxd.deansoffice.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ppxxd.deansoffice.exception.EducationNotFoundException;
import ru.ppxxd.deansoffice.model.Education;
import ru.ppxxd.deansoffice.model.Group;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Component("EducationStorage")
@RequiredArgsConstructor
@Slf4j
public class EducationStorage {
    private final JdbcTemplate jdbcTemplate;

    public Education addEducation(Education education) {
        String sqlQuery = "INSERT INTO EDUCATIONS (TYPE, SPECIALIZATION, UNIVERSITY_NAME, DATE_END)" +
                " VALUES (?,?,?,?)";
        KeyHolder id = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"education_id"});
            ps.setString(1, education.getType());
            ps.setString(2, education.getSpecialization());
            ps.setString(3, education.getUniversityName());
            ps.setDate(4, Date.valueOf(education.getDateEnd()));
            return ps;
        }, id);

        education.setEducationID(Integer.parseInt(Objects.requireNonNull(id.getKey()).toString()));
        log.info("Новому образованию преподавателя присвоен id {}", education.getEducationID());
        return education;
    }

    public boolean checkEducationExists(Integer educationID) {
        String sqlQuery = "SELECT EDUCATION_ID FROM EDUCATIONS WHERE EDUCATION_ID = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, educationID);
        if (!rowSet.next()) {
            throw new EducationNotFoundException(String.format("Образование с id %d не найдено.", educationID));
        }
        return true;
    }

    public Education updateEducation(Education education) {
        String sqlQuery = "UPDATE EDUCATIONS SET " +
                "TYPE = ?," +
                "SPECIALIZATION = ?," +
                "UNIVERSITY_NAME = ?," +
                "DATE_END = ?" +
                "WHERE EDUCATION_ID = ?";
        jdbcTemplate.update(sqlQuery, education.getType(), education.getSpecialization(), education.getUniversityName(),
                education.getDateEnd(), education.getEducationID());
        return education;
    }

    public Education deleteEducation(Education education) {
        String sql = "DELETE FROM EDUCATIONS WHERE EDUCATION_ID = ?";
        jdbcTemplate.update(sql, education.getEducationID());
        return education;
    }

    public Education getEducationByID(Integer id) {
        String sqlQuery = "SELECT * FROM EDUCATIONS WHERE EDUCATION_ID = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToEducaiton, id);
    }

    private Education mapRowToEducaiton(ResultSet rs, int rowNum) throws SQLException {
        return Education.builder()
                .educationID(rs.getInt("education_id"))
                .type(rs.getString("type"))
                .specialization(rs.getString("specialization"))
                .universityName(rs.getString("university_name"))
                .dateEnd(Objects.requireNonNull(rs.getDate("date_end")).toLocalDate())
                .build();
    }

    public Education getEducationByTeacherID(int teacherID) {
        String sqlQuery = "SELECT * FROM EDUCATIONS WHERE EDUCATION_ID IN " +
                "(SELECT TEACHERS_EDUCATIONS.EDUCATION_ID  FROM TEACHERS_EDUCATIONS WHERE TEACHER_ID = ?)";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToEducaiton, teacherID);
    }

    public Education addTeacherEducation(int teacherID, Education education) {
        String sql = "INSERT INTO TEACHERS_EDUCATIONS (teacher_id, education_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, teacherID, education.getEducationID());
        return education;
    }

    public Education updateTeacherEducation(int teacherID, Education education) {
        String sql = "INSERT INTO TEACHERS_EDUCATIONS (teacher_id, education_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, teacherID, education.getEducationID());
        return education;
    }

    public Education deleteTeacherEducation(int teacherID, Education education) {
        String sql = "DELETE FROM TEACHERS_EDUCATIONS WHERE TEACHER_ID = ? AND EDUCATION_ID = ?";
        jdbcTemplate.update(sql, teacherID, education.getEducationID());
        return education;
    }
}
