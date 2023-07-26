package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserDb implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sql);
        List<User> users = new ArrayList<>();
        while (srs.next()) {
            users.add(userMap(srs));
        }
        return users;
    }

    @Override
    public User create(User user) {
        Map<String, Object> keys = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("users")
                .usingColumns("user_name", "login", "email", "birthday")
                .usingGeneratedKeyColumns("user_id")
                .executeAndReturnKeyHolder(Map.of(
                        "user_name", user.getName(),
                        "login", user.getLogin(),
                        "email", user.getEmail(),
                        "birthday", java.sql.Date.valueOf(user.getBirthday())))
                .getKeys();
        user.setId((Integer) keys.get("user_id"));
        return user;
    }

    @Override
    public User update(User user) {
        getById(user.getId());
        String sqlQuery = "UPDATE users "
                + "SET user_name = ?, "
                + "login = ?, "
                + "email = ?, "
                + "birthday = ? "
                + "WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, user.getName(), user.getLogin(),
                user.getEmail(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public void delete(int userId) {
        String sql = "DELETE FROM users WHERE user_id = " + userId;
    }

    @Override
    public User getById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sql, userId);
        if (srs.next()) {
            return userMap(srs);
        } else {
            throw new NotFoundException("User with ID=" + userId + " not found!");
        }
    }

    public void addFriend(int userId, int friendId) {
        String sql = "INSERT INTO friends (user_id, friend_id, status) "
                + "VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, userId, friendId, true);
    }

    public void removeFriend(int userId, int friendId) {
        String sql = "DELETE friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    public List<User> getFriends(int userId) {
        List<User> friends = new ArrayList<>();
        String sql = "SELECT * FROM users " +
                "WHERE users.user_id IN (SELECT friend_id from friends WHERE user_id = ?)";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sql, userId);
        while (srs.next()) {
            friends.add(UserDb.userMap(srs));
        }
        return friends;
    }

    public List<User> getCommonFriends(int friend1, int friend2) {
        List<User> commonFriends = new ArrayList<>();
        String sqlQuery = "SELECT * FROM users "
                + "WHERE users.user_id IN (SELECT friend_id from friends "
                + "WHERE user_id IN (?, ?) "
                + "AND friend_id NOT IN (?, ?))";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery, friend1, friend2, friend1, friend2);
        while (srs.next()) {
            commonFriends.add(UserDb.userMap(srs));
        }
        return commonFriends;
    }

    public boolean isFriend(int userId, int friendId) {
        String sql = "SELECT * FROM friends WHERE "
                + "user_id = ? AND friends_id = ?";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sql, userId, friendId);
        return srs.next();
    }

    private static User userMap(SqlRowSet srs) {
        int id = srs.getInt("user_id");
        String name = srs.getString("user_name");
        String login = srs.getString("login");
        String email = srs.getString("email");
        LocalDate birthday = Objects.requireNonNull(srs.getTimestamp("birthday"))
                .toLocalDateTime().toLocalDate();
        return User.builder()
                .id(id)
                .name(name)
                .login(login)
                .email(email)
                .birthday(birthday)
                .build();
    }
}