package ru.yandex.practicum.filmorate.dao.like;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Repository
public class LikeStorageDaoImpl implements LikeStorageDao {
    protected final JdbcTemplate jdbcTemplate;

    public LikeStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String getInsertQuery() {
        return "INSERT INTO likes (film_id, user_id) VALUES (?,?)";
    }

    private String getSelectPopularQuery() {
        return "SELECT l.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.mpa_name " +
                "FROM (SELECT film_id, COUNT (user_id) AS like_count " +
                "FROM likes " +
                "GROUP BY film_id " +
                "ORDER BY like_count DESC) AS l " +
                "LEFT OUTER JOIN films AS f ON l.film_id = f.film_id " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                "LIMIT ?";
    }

    private String getSelectAllQuery() {
        return "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id";
    }

    private String getDeleteQuery() {
        return "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
    }

    @Override
    public void create(long filmId, long userId) {
        jdbcTemplate.update(getInsertQuery(), filmId, userId);
    }

    @Override
    public Collection<Film> readPopular(String count) {
        if (count == null) {
            count = String.valueOf(10);
        }

        Collection<Film> popularFilms = jdbcTemplate.query(getSelectPopularQuery(), new FilmMapper(), count);
        Collection<Film> allFilms = jdbcTemplate.query(getSelectAllQuery(), new FilmMapper());

        if (popularFilms.isEmpty()) {
            return allFilms;
        } else {
            return popularFilms;
        }
    }

    @Override
    public void delete(long filmId, long userId) {
        jdbcTemplate.update(getDeleteQuery(), filmId, userId);
    }

    public boolean isExists(long userId) {
        try {
            jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?", new UserMapper(), userId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
