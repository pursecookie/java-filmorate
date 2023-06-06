package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendshipMapper implements RowMapper<Friendship> {
    @Override
    public Friendship mapRow(ResultSet rs, int rowNum) throws SQLException {
        Friendship friendship = new Friendship();

        friendship.setId(rs.getLong("friendship_id"));
        friendship.setUserFrom(rs.getLong("user_from"));
        friendship.setUserTo(rs.getLong("user_to"));
        friendship.setApproved(rs.getBoolean("status"));

        return friendship;
    }
}
