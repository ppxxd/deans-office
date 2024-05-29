package ru.ppxxd.deansoffice.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ppxxd.deansoffice.exception.ChairNotFoundException;
import ru.ppxxd.deansoffice.model.Chair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component("ChairStorage")
@RequiredArgsConstructor
@Slf4j
public class ChairStorage {
    private final JdbcTemplate jdbcTemplate;

    public Chair addChair(Chair chair) {
        String sqlQuery = "INSERT INTO CHAIRS (NAME, HEAD_LAST_NAME, HEAD_FIRST_NAME, HEAD_PATRONYMIC, CHAIR_PHONE_NUMBER)" +
                " VALUES (?,?,?,?, ?)";
        KeyHolder id = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"chair_id"});
            ps.setString(1, chair.getName());
            ps.setString(2, chair.getHeadLastName());
            ps.setString(3, chair.getHeadFirstName());
            ps.setString(4, chair.getHeadPatronymic());
            ps.setInt(4, chair.getChairPhoneNumber());
            return ps;
        }, id);

        chair.setChairID(Integer.parseInt(Objects.requireNonNull(id.getKey()).toString()));
        log.info("Новой кафедре присвоен id {}", chair.getChairID());
        return chair;
    }

    public boolean checkChairExist(Integer chairID) {
        String sqlQuery = "SELECT CHAIR_ID FROM CHAIRS WHERE CHAIR_ID = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, chairID);
        if (!rowSet.next()) {
            throw new ChairNotFoundException(String.format("Кафедра с id %d не найдена.", chairID));
        }
        return true;
    }

    public Chair updateChair(Chair chair) {
        String sqlQuery = "UPDATE CHAIRS SET " +
                "NAME = ?," +
                "HEAD_LAST_NAME = ?," +
                "HEAD_FIRST_NAME = ?," +
                "HEAD_PATRONYMIC = ?," +
                "CHAIR_PHONE_NUMBER = ? " +
                "WHERE CHAIR_ID = ?";
        jdbcTemplate.update(sqlQuery, chair.getName(), chair.getHeadLastName(), chair.getHeadFirstName(),
                chair.getHeadPatronymic(), chair.getChairPhoneNumber(), chair.getChairID());
        return chair;
    }

    public Chair deleteChair(Chair chair) {
        String sql = "DELETE FROM CHAIRS WHERE CHAIR_ID = ?";
        jdbcTemplate.update(sql, chair.getChairID());
        return chair;
    }

    public Chair getChairByID(Integer id) {
        String sqlQuery = "SELECT * FROM CHAIRS WHERE CHAIR_ID = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToChair, id);
    }

    public List<Chair> getChairs() {
        String sqlQuery = "SELECT * FROM CHAIRS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToChair);
    }

    private Chair mapRowToChair(ResultSet rs, int rowNum) throws SQLException {
        return Chair.builder()
                .chairID(rs.getInt("chair_id"))
                .headLastName(rs.getString("head_last_name"))
                .headFirstName(rs.getString("head_first_name"))
                .headPatronymic(rs.getString("head_patronymic"))
                .chairPhoneNumber(rs.getInt("chair_phone_number"))
                .build();
    }
}
