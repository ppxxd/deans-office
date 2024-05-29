package ru.ppxxd.deansoffice.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.ppxxd.deansoffice.exception.StudentNotFoundException;
import ru.ppxxd.deansoffice.exception.UserNotFoundException;
import ru.ppxxd.deansoffice.model.Student;
import ru.ppxxd.deansoffice.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component("UserStorage")
@RequiredArgsConstructor
@Slf4j
public class UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public User getUserByID(Integer id) {
        String sqlQuery = "SELECT * FROM USERS WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
    }

    public boolean checkUserExists(Integer id) {
        String sqlQuery = "SELECT id FROM USERS WHERE id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (!rowSet.next()) {
            throw new StudentNotFoundException(String.format("Пользователь с id %d не найден.", id));
        }
        return true;
    }

    public User addUser(User user) {
        String sqlQuery = "INSERT INTO USERS (login, password, role) VALUES (?,?,?,?)";
        KeyHolder id = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"id"});
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().name());
            return ps;
        }, id);

        user.setId(Integer.parseInt(Objects.requireNonNull(id.getKey()).toString()));
        log.info("Новому пользователю присвоен id {}", user.getId());


        return user;
    }

    public User updateUser(User user) {
        String sqlQuery = "UPDATE USERS SET " +
                "LOGIN = ?," +
                "PASSWORD = ?," +
                "ROLE = ?" +
                "WHERE id = ?";
        jdbcTemplate.update(sqlQuery, user.getLogin(), user.getPassword(),
                user.getRole(), user.getId());
        return user;
    }

    public User deleteUser(User user) {
        String sql = "DELETE FROM Users WHERE id = ?";
        jdbcTemplate.update(sql, user.getId());
        return user;
    }

    public List<User> getUsers() {
        String sqlQuery = "SELECT * FROM USERS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .login(rs.getString("login"))
                .password(rs.getString("passowrd"))
                .role(User.Role.valueOf(rs.getString("role")))
                .build();
    }

    public User findUserByLogin(String login) {
        String sqlQuery = "SELECT * FROM USERS WHERE login = ?";
        if (sqlQuery.isBlank()) {
            throw new UserNotFoundException(String.format("Пользователь с логином %s не найден", login));
        }
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, login);
    }
}
