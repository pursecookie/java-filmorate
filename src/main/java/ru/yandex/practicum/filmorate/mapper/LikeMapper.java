package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Like;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeMapper implements RowMapper<Like> {
    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        Like like = new Like();

        like.setId(rs.getLong("like_id"));
        like.setFilmId(rs.getLong("film_id"));
        like.setUserId(rs.getLong("user_id"));

        return like;
    }
}
