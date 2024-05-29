package ru.ppxxd.deansoffice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ppxxd.deansoffice.model.Student;
import ru.ppxxd.deansoffice.model.User;
import ru.ppxxd.deansoffice.storage.UserStorage;
import ru.ppxxd.deansoffice.util.HashUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User createUser(User user) {
        user.setPassword(HashUtil.sha256(user.getPassword()));
        user = userStorage.addUser(user);
        return user;
    }

    public User findByUsername(String login) {
        return userStorage.findUserByLogin(login);
    }

    public boolean loginUser(String login, String password) {
        User user = userStorage.findUserByLogin(login);
        if (user != null) {
            String hashedPassword = HashUtil.sha256(password);
            return user.getPassword().equals(hashedPassword);
        }
        return false;
    }

    public User updateUser(User user) {
        userStorage.checkUserExists(user.getId());
        return userStorage.updateUser(user);
    }

    public User deleteUser(User user) {
        userStorage.checkUserExists(user.getId());
        return userStorage.deleteUser(user);
    }

    public User getUser(Integer id) {
        userStorage.checkUserExists(id);
        return userStorage.getUserByID(id);
    }

    public List<User> getAll() {
        return userStorage.getUsers();
    }
}
