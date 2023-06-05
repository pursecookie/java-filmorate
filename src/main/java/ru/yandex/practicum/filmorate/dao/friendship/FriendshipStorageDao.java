package ru.yandex.practicum.filmorate.dao.friendship;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.FriendshipMapper;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Repository
public class FriendshipStorageDao {
    private final JdbcTemplate jdbcTemplate;

    public FriendshipStorageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String getInsertQuery() {
        return "INSERT INTO friendships (user_from, user_to, status) VALUES (?,?,?)";
    }

    private String getSelectAllQuery() {
        return "SELECT f.user_to AS user_id, u.login, u.name, u.email, u.birthday " +
                "FROM friendships AS f " +
                "LEFT OUTER JOIN users AS u ON f.user_to = u.user_id " +
                "WHERE f.user_from = ?" +
                "ORDER BY user_id";
    }

    private String getDeleteQuery() {
        return "DELETE FROM friendships WHERE user_from = ? AND user_to = ?";
    }

    public void create(long userFrom, long userTo) {
        boolean status;

        List<Long> friendship = jdbcTemplate.query("SELECT user_to FROM friendships " +
                        "WHERE user_from = ? AND user_to = ? ",
                (rs, rowNum) -> rs.getLong("user_to"), userTo, userFrom);

        if (friendship.contains(userFrom)) {
            status = true;
            jdbcTemplate.update("UPDATE friendships SET status = ? WHERE user_from = ?", true, userTo);
        } else {
            status = false;
        }

        jdbcTemplate.update(getInsertQuery(), userFrom, userTo, status);
    }

    public Collection<User> readAll(long userId) {
        return jdbcTemplate.query(getSelectAllQuery(), new UserMapper(), userId);
    }

    public Collection<User> readCommon(long userId, long otherId) {
        Collection<User> commonFriends = new HashSet<>();

        for (User user : readAll(userId)) {
            for (User otherUser : readAll(otherId)) {
                if (user.equals(otherUser)) {
                    commonFriends.add(user);
                }
            }
        }

        return commonFriends;
    }

    public void delete(long userId, long friendId) {
        Friendship friendship = jdbcTemplate.queryForObject("SELECT * FROM friendships " +
                        "WHERE user_from = ? AND user_to = ?",
                new FriendshipMapper(), userId, friendId);

        if (friendship.isApproved()) {
            jdbcTemplate.update("UPDATE friendships SET status = ? WHERE user_from = ? AND user_to = ?",
                    false, friendId, userId);
        }

        jdbcTemplate.update(getDeleteQuery(), userId, friendId);
    }

    public boolean isExists(long friendId) {
        try {
            jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?", new UserMapper(), friendId);

            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
