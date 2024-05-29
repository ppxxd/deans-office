package ru.ppxxd.deansoffice.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ppxxd.deansoffice.exception.SubjectNotFoundException;
import ru.ppxxd.deansoffice.model.Subject;
import ru.ppxxd.deansoffice.model.Subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component("SubjectStorage")
@RequiredArgsConstructor
@Slf4j
public class SubjectStorage {
    private final JdbcTemplate jdbcTemplate;

    public Subject addSubject(Subject subject) {
        String sqlQuery = "INSERT INTO SUBJECTS (NAME, SUBJECT_COEFFICIENT)" +
                " VALUES (?,?)";
        KeyHolder id = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"subject_id"});
            ps.setString(1, subject.getName());
            ps.setDouble(2, subject.getSubjectCoefficient());
            return ps;
        }, id);

        subject.setSubjectID(Integer.parseInt(Objects.requireNonNull(id.getKey()).toString()));
        log.info("Новому предмету присвоен id {}", subject.getSubjectID());
        return subject;
    }

    public boolean checkSubjectExist(Integer id) {
        String sqlQuery = "SELECT SUBJECT_ID FROM SUBJECTS WHERE SUBJECT_ID = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!rowSet.next()) {
            throw new SubjectNotFoundException(String.format("Предмет с id %d не найден.", id));
        }
        return true;
    }

    public Subject updateSubject(Subject subject) {
        String sqlQuery = "UPDATE SUBJECTS SET " +
                "NAME = ?," +
                "SUBJECT_COEFFICIENT = ?" +
                "WHERE SUBJECT_ID = ?";
        jdbcTemplate.update(sqlQuery, subject.getName(), subject.getSubjectCoefficient(), subject.getSubjectID());
        return subject;
    }

    public Subject deleteSubject(Subject subject) {
        String sql = "DELETE FROM SUBJECTS WHERE SUBJECT_ID = ?";
        jdbcTemplate.update(sql, subject.getSubjectID());
        return subject;
    }

    public Subject getSubjectByID(Integer id) {
        String sqlQuery = "SELECT * FROM SUBJECTS WHERE SUBJECT_ID = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToSubject, id);
    }

    public List<Subject> getSubjects() {
        String sqlQuery = "SELECT * FROM SUBJECTS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToSubject);
    }

    private Subject mapRowToSubject(ResultSet rs, int rowNum) throws SQLException {
        return Subject.builder()
                .subjectID(rs.getInt("subject_id"))
                .name(rs.getString("name"))
                .subjectCoefficient(rs.getDouble("subject_coefficient"))
                .build();
    }
}
