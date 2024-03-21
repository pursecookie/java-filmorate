package ru.yandex.practicum.filmorate.dao.like;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository
public class LikeStorageDao {
    private final JdbcTemplate jdbcTemplate;

    public LikeStorageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String getInsertQuery() {
        return "INSERT INTO films_likes (film_id, user_id) VALUES (?,?)";
    }

    private String getSelectAllQuery() {
        return "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                "LIMIT ?";
    }

    private String getDeleteQuery() {
        return "DELETE FROM films_likes WHERE film_id = ? AND user_id = ?";
    }

    public void create(long filmId, long userId) {
        jdbcTemplate.update(getInsertQuery(), filmId, userId);
    }

    public Collection<Film> readPopular(Long count) {
        String sqlReadPopularFilms = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN (SELECT film_id, COUNT(user_id) AS like_count " +
                "FROM films_likes " +
                "GROUP BY film_id) AS l ON f.film_id = l.film_id " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                "ORDER BY like_count DESC " +
                "LIMIT ?";

        Collection<Film> popularFilms = jdbcTemplate.query(sqlReadPopularFilms, new FilmMapper(), count);
        Collection<Film> allFilms = jdbcTemplate.query(getSelectAllQuery(), new FilmMapper(), count);

        if (popularFilms.isEmpty()) {
            return allFilms;
        } else {
            return popularFilms;
        }
    }

    public Collection<Film> readPopularByGenre(Long count, Long genreId) {
        String sqlReadPopularFilms = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN (SELECT film_id, COUNT(user_id) AS like_count " +
                "FROM films_likes " +
                "GROUP BY film_id) AS l ON f.film_id = l.film_id " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                "LEFT OUTER JOIN films_genres AS fg ON f.film_id = fg.film_id " +
                "WHERE fg.genre_id = ?" +
                "ORDER BY like_count DESC " +
                "LIMIT ?";

        return jdbcTemplate.query(sqlReadPopularFilms, new FilmMapper(), genreId, count);
    }

    public Collection<Film> readPopularByYear(Long count, Integer year) {
        String sqlReadPopularFilms = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN (SELECT film_id, COUNT(user_id) AS like_count " +
                "FROM films_likes " +
                "GROUP BY film_id) AS l ON f.film_id = l.film_id " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                "WHERE EXTRACT(year from f.release_date) = ? " +
                "ORDER BY like_count DESC " +
                "LIMIT ?";

        return jdbcTemplate.query(sqlReadPopularFilms, new FilmMapper(), year, count);
    }

    public Collection<Film> readPopularByGenreAndYear(Long count, Long genreId, Integer year) {
        String sqlReadPopularFilms = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN (SELECT film_id, COUNT(user_id) AS like_count " +
                "FROM films_likes " +
                "GROUP BY film_id) AS l ON f.film_id = l.film_id " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                "LEFT OUTER JOIN films_genres AS fg ON f.film_id = fg.film_id " +
                "WHERE fg.genre_id = ?" +
                "AND EXTRACT(year from f.release_date) = ? " +
                "ORDER BY like_count DESC " +
                "LIMIT ?";

        return jdbcTemplate.query(sqlReadPopularFilms, new FilmMapper(), genreId, year, count);
    }

    public Collection<Film> readFilmRecommendations(long userId) {
        String sqlFilmIds = "SELECT l.film_id " +
                "FROM films_likes AS l " +
                "WHERE l.user_id IN (SELECT user_id " +
                "FROM films_likes AS l1 " +
                "WHERE l1.film_id IN (SELECT l2.film_id " +
                "FROM films_likes AS l2 " +
                "WHERE l2.user_id = ?) " +
                "AND l1.user_id <> ? " +
                "AND EXISTS (SELECT l3.film_id " +
                "FROM films_likes AS l3 WHERE l3.film_id NOT IN (SELECT l4.film_id  " +
                "FROM films_likes AS l4 " +
                "WHERE l4.user_id = ?)) " +
                "GROUP BY l1.user_id " +
                "ORDER BY COUNT(l.film_id) " +
                "LIMIT 1) " +
                "GROUP BY l.film_id " +
                "HAVING l.film_id NOT IN (SELECT l5.film_id " +
                "FROM films_likes AS l5 " +
                "WHERE l5.user_id = ?) " +
                "ORDER BY l.film_id";

        Set<Long> filmIds = new HashSet<>(jdbcTemplate.query(sqlFilmIds,
                (rs, rowNum) -> rs.getLong("film_id"),
                userId, userId, userId, userId));

        String inSql = String.join(",", Collections.nCopies(filmIds.size(), "?"));

        return jdbcTemplate.query(
                String.format("SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                        "f.rating_id, r.mpa_name " +
                        "FROM films AS f " +
                        "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                        "WHERE f.film_id IN (%s)", inSql), new FilmMapper(), filmIds.toArray());
    }

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
