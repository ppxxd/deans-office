package ru.ppxxd.deansoffice.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ppxxd.deansoffice.exception.ChairNotFoundException;
import ru.ppxxd.deansoffice.exception.GroupNotFoundException;
import ru.ppxxd.deansoffice.model.Chair;
import ru.ppxxd.deansoffice.model.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component("GroupStorage")
@RequiredArgsConstructor
@Slf4j
public class GroupStorage {
    private final JdbcTemplate jdbcTemplate;

    public Group addGroup(Group group) {
        String sqlQuery = "INSERT INTO GROUPS (NAME, COURSE, EDUCATION_DIRECTION)" +
                " VALUES (?,?,?)";
        KeyHolder id = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"id"});
            ps.setString(1, group.getName());
            ps.setInt(2, group.getCourse());
            ps.setString(3, group.getEducationDirection());
            return ps;
        }, id);

        group.setId(Integer.parseInt(Objects.requireNonNull(id.getKey()).toString()));
        log.info("Новой группе присвоен id {}", group.getId());
        return group;
    }

    public boolean checkGroupExist(Integer id) {
        String sqlQuery = "SELECT ID FROM GROUPS WHERE ID = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!rowSet.next()) {
            throw new GroupNotFoundException(String.format("Группа с id %d не найдена.", id));
        }
        return true;
    }

    public Group updateGroup(Group group) {
        String sqlQuery = "UPDATE GROUPS SET " +
                "NAME = ?," +
                "COURSE = ?," +
                "EDUCATION_DIRECTION = ?" +
                "WHERE ID = ?";
        jdbcTemplate.update(sqlQuery, group.getName(), group.getCourse(), group.getEducationDirection(), group.getId());
        return group;
    }

    public Group deleteGroup(Group group) {
        String sql = "DELETE FROM GROUPS WHERE ID = ?";
        jdbcTemplate.update(sql, group.getId());
        return group;
    }

    public Group getGroupByID(Integer id) {
        String sqlQuery = "SELECT * FROM GROUPS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGroup, id);
    }

    public List<Group> getGroups() {
        String sqlQuery = "SELECT * FROM GROUPS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGroup);
    }

    private Group mapRowToGroup(ResultSet rs, int rowNum) throws SQLException {
        return Group.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .course(rs.getInt("course"))
                .educationDirection(rs.getString("education_direction"))
                .build();
    }

    public Group getGroupByStudentID(int studentID) {
        String sqlQuery = "SELECT * FROM GROUPS WHERE ID IN " +
                "(SELECT GROUP_ID  FROM GROUPS_STUDENTS WHERE STUDENT_ID = ?)";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGroup, studentID);
    }

    public Group addStudentToGroup(int studentID, Group group) {
        String sql = "INSERT INTO GROUPS_STUDENTS (STUDENT_ID, GROUP_ID) VALUES (?, ?)";
        jdbcTemplate.update(sql, studentID, group.getId());
        return group;
    }

    public Group updateStudentGroup(int studentID, Group group) {
        String sql = "INSERT INTO GROUPS_STUDENTS (STUDENT_ID, GROUP_ID) VALUES (?, ?)";
        jdbcTemplate.update(sql, studentID, group.getId());
        return group;
    }

    public Group deleteStudentFromGroup(int studentID, Group group) {
        String sql = "DELETE FROM GROUPS_STUDENTS WHERE STUDENT_ID = ? AND GROUP_ID = ?";
        jdbcTemplate.update(sql, studentID, group.getId());
        return group;
    }
}
