package ru.ppxxd.deansoffice.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ppxxd.deansoffice.exception.AssessmentNotFoundException;
import ru.ppxxd.deansoffice.model.Assessment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component("AssessmentStorage")
@RequiredArgsConstructor
@Slf4j
public class AssessmentStorage {
    private final JdbcTemplate jdbcTemplate;

    public List<Assessment> getAssessments() {
        String sqlQuery = "SELECT * FROM students";
        return jdbcTemplate.query(sqlQuery, this::mapRowToAssessment);
    }

    public List<Assessment> getAssessmentsByStudentID(Integer studentID) {
        String sqlQuery = "SELECT * FROM ASSESSMENTS WHERE ASSESSMENT_ID IN " +
                "(SELECT ASSESSMENT_ID FROM ASSESSMENTS_LISTS WHERE STUDENT_ID = ?)";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery, studentID);
        return getAssessmentsFromRowSet(rs);
    }

    public Assessment addAssessment(Integer studentID, Assessment assessment) {
        String sqlQuery = "INSERT INTO assessments (SUBJECT_ID, MARK, TYPE, RECEIVED) VALUES (?,?,?,?)";
        KeyHolder id = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"id"});
            ps.setInt(1, assessment.getSubjectID());
            ps.setInt(2, assessment.getMark());
            ps.setString(3, assessment.getType());
            ps.setDate(4, Date.valueOf(assessment.getReceived()));
            return ps;
        }, id);

        assessment.setAssessmentID(Integer.parseInt(Objects.requireNonNull(id.getKey()).toString()));

        String sql = "INSERT INTO assessments_lists (student_id, assessment_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, studentID, assessment.getAssessmentID());

        log.info("Новой оценке студента id {} присвоен id {}", studentID, assessment.getAssessmentID());
        return assessment;
    }

    public boolean checkAssessmentExists(Integer assessmentID) {
        String sqlQuery = "SELECT ASSESSMENT_ID FROM assessments WHERE ASSESSMENT_ID = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, assessmentID);
        if (!rowSet.next()) {
            throw new AssessmentNotFoundException(String.format("Оценка с id %d не найдена.", assessmentID));
        }
        return true;
    }

    public Assessment updateAssessment(Assessment assessment) {
        String sqlQuery = "UPDATE ASSESSMENTS SET " +
                "SUBJECT_ID = ?," +
                "MARK = ?," +
                "TYPE = ?," +
                "RECEIVED = ?" +
                "WHERE ASSESSMENT_ID = ?";
        jdbcTemplate.update(sqlQuery, assessment.getSubjectID(), assessment.getMark(), assessment.getType(),
                assessment.getReceived(), assessment.getAssessmentID());
        return assessment;
    }

    public Assessment deleteAssessment(Integer studentID, Assessment assessment) {
        String sqlQuery = "DELETE FROM assessments_lists WHERE student_id = ?";
        jdbcTemplate.update(sqlQuery, studentID);
        sqlQuery = "DELETE FROM ASSESSMENTS WHERE ASSESSMENT_ID = ?";
        jdbcTemplate.update(sqlQuery, assessment.getAssessmentID());
        return assessment;
    }

    private Assessment mapRowToAssessment(ResultSet rs, int rowNum) throws SQLException {
        return Assessment.builder()
                .assessmentID(rs.getInt("assessment_id"))
                .subjectID(rs.getInt("subject_id"))
                .mark(rs.getInt("mark"))
                .type(rs.getString("type"))
                .received(Objects.requireNonNull(rs.getDate("received")).toLocalDate())
                .build();
    }

    private List<Assessment> getAssessmentsFromRowSet(SqlRowSet rs) {
        List<Assessment> assessments = new ArrayList<>();
        while (rs.next()) {
            assessments.add(
                    Assessment.builder()
                            .assessmentID(rs.getInt("assessment_id"))
                            .subjectID(rs.getInt("subject_id"))
                            .mark(rs.getInt("mark"))
                            .type(rs.getString("type"))
                            .received(Objects.requireNonNull(rs.getDate("received")).toLocalDate())
                            .build()
            );
        }
        return assessments;
    }
}
