package ru.yandex.practicum.filmorate.dao.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

@Repository
public class UserStorageDaoImpl extends DataStorageDaoImpl<User> implements UserStorageDao {
    public UserStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    protected String getInsertQuery() {
        return "INSERT INTO users (login, name, email, birthday) VALUES (?,?,?,?)";
    }

    protected String getSelectQuery() {
        return "SELECT * FROM users WHERE user_id = ?";
    }

    protected String getSelectAllQuery() {
        return "SELECT * FROM users";
    }

    protected String getUpdateQuery() {
        return "UPDATE users SET login = ?, name = ?, email = ?, birthday = ? WHERE user_id = ?";
    }

    protected String getDeleteQuery() {
        return "DELETE FROM users WHERE user_id = ?";
    }

    protected RowMapper<User> getMapper() {
        return new UserMapper();
    }

    @Override
    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        jdbcTemplate.update(getInsertQuery(), user.getLogin(), user.getName(), user.getEmail(), user.getBirthday());

        return jdbcTemplate.queryForObject(getSelectAllQuery() + " WHERE login = ?", getMapper(), user.getLogin());
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update(getUpdateQuery(), user.getLogin(), user.getName(),
                user.getEmail(), user.getBirthday(), user.getId());

        return read(user.getId());
    }
}