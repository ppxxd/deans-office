package ru.ppxxd.deansoffice.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ppxxd.deansoffice.exception.PositionNotFoundException;
import ru.ppxxd.deansoffice.exception.StudentNotFoundException;
import ru.ppxxd.deansoffice.model.Position;
import ru.ppxxd.deansoffice.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("PositionStorage")
@RequiredArgsConstructor
@Slf4j
public class PositionStorage {
    private final JdbcTemplate jdbcTemplate;

    public boolean checkPositionExists(Integer id) {
        String sqlQuery = "SELECT POSITION_ID FROM POSITIONS WHERE POSITION_ID = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!rowSet.next()) {
            throw new PositionNotFoundException(String.format("Должность с id %d не найдена.", id));
        }
        return true;
    }

    public Position getPositionByID(Integer id) {
        String sqlQuery = "SELECT * FROM POSITIONS WHERE POSITION_ID = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToPositions, id);
    }

    public List<Position> getPositions() {
        String sqlQuery = "SELECT * FROM POSITIONS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToPositions);
    }

    private Position mapRowToPositions(ResultSet rs, int rowNum) throws SQLException {
        return Position.builder()
                .positionID(rs.getInt("position_id"))
                .name(rs.getString("name"))
                .responsibilities(rs.getString("responsibilities"))
                .build();
    }
}
