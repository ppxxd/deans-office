package ru.ppxxd.deansoffice.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ppxxd.deansoffice.exception.EducationNotFoundException;
import ru.ppxxd.deansoffice.exception.WorkExperienceNotFoundException;
import ru.ppxxd.deansoffice.model.Education;
import ru.ppxxd.deansoffice.model.WorkExperience;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Component("WorkExperienceStorage")
@RequiredArgsConstructor
@Slf4j
public class WorkExperienceStorage {
    private final JdbcTemplate jdbcTemplate;

    public WorkExperience addWorkExperience(WorkExperience experience) {
        String sqlQuery = "INSERT INTO WORK_EXPERIENCES (company_name, position_name, date_start, date_end, responsibilities)" +
                " VALUES (?,?,?,?, ?)";
        KeyHolder id = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"work_experience_id"});
            ps.setString(1, experience.getCompanyName());
            ps.setString(2, experience.getPositionName());
            ps.setDate(3, Date.valueOf(experience.getDateStart()));
            ps.setDate(4, Date.valueOf(experience.getDateEnd()));
            ps.setString(5, experience.getResponsibilities());
            return ps;
        }, id);

        experience.setWorkExperienceID(Integer.parseInt(Objects.requireNonNull(id.getKey()).toString()));
        log.info("Новому опыту работу преподавателя присвоен id {}", experience.getWorkExperienceID());
        return experience;
    }

    public boolean checkWorkExperienceExists(Integer experienceID) {
        String sqlQuery = "SELECT WORK_EXPERIENCE_ID FROM WORK_EXPERIENCES WHERE WORK_EXPERIENCE_ID = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, experienceID);
        if (!rowSet.next()) {
            throw new WorkExperienceNotFoundException(String.format("Опыт работы с id %d не найдено.", experienceID));
        }
        return true;
    }

    public WorkExperience updateWorkExperience(WorkExperience experience) {
        String sqlQuery = "UPDATE WORK_EXPERIENCES SET " +
                "COMPANY_NAME = ?," +
                "POSITION_NAME = ?," +
                "DATE_START = ?," +
                "DATE_END = ?," +
                "RESPONSIBILITIES = ?" +
                "WHERE WORK_EXPERIENCE_ID = ?";
        jdbcTemplate.update(sqlQuery, experience.getCompanyName(), experience.getPositionName(), experience.getDateStart(),
                experience.getDateEnd(), experience.getResponsibilities());
        return experience;
    }

    public WorkExperience deleteWorkExperience(WorkExperience experience) {
        String sql = "DELETE FROM WORK_EXPERIENCES WHERE WORK_EXPERIENCE_ID = ?";
        jdbcTemplate.update(sql, experience.getWorkExperienceID());
        return experience;
    }

    public WorkExperience getWorkExperienceByID(Integer id) {
        String sqlQuery = "SELECT * FROM WORK_EXPERIENCES WHERE WORK_EXPERIENCE_ID = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToWorkExperience, id);
    }

    private WorkExperience mapRowToWorkExperience(ResultSet rs, int rowNum) throws SQLException {
        return WorkExperience.builder()
                .companyName(rs.getString("company_name"))
                .positionName(rs.getString("position_name"))
                .dateStart(Objects.requireNonNull(rs.getDate("date_start")).toLocalDate())
                .dateEnd(Objects.requireNonNull(rs.getDate("date_end")).toLocalDate())
                .responsibilities(rs.getString("responsibilities"))
                .build();
    }

    public WorkExperience getEducationByTeacherID(int teacherID) {
        String sqlQuery = "SELECT * FROM WORK_EXPERIENCES WHERE WORK_EXPERIENCES.WORK_EXPERIENCE_ID IN " +
                "(SELECT TEACHERS_EXPERIENCES.WORK_EXPERIENCE_ID  FROM TEACHERS_EXPERIENCES WHERE TEACHER_ID = ?)";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToWorkExperience, teacherID);
    }

    public WorkExperience addTeacherWorkExperience(int teacherID, WorkExperience experience) {
        String sql = "INSERT INTO TEACHERS_EXPERIENCES (teacher_id, WORK_EXPERIENCE_ID) VALUES (?, ?)";
        jdbcTemplate.update(sql, teacherID, experience.getWorkExperienceID());
        return experience;
    }

    public WorkExperience updateTeacherWorkExperience(int teacherID, WorkExperience experience) {
        String sql = "INSERT INTO TEACHERS_EXPERIENCES (teacher_id, WORK_EXPERIENCE_ID) VALUES (?, ?)";
        jdbcTemplate.update(sql, teacherID, experience.getWorkExperienceID());
        return experience;
    }

    public WorkExperience deleteTeacherWorkExperience(int teacherID, WorkExperience experience) {
        String sql = "DELETE FROM TEACHERS_EXPERIENCES WHERE TEACHER_ID = ? AND WORK_EXPERIENCE_ID = ?";
        jdbcTemplate.update(sql, teacherID, experience.getWorkExperienceID());
        return experience;
    }
}
