package ru.yandex.practicum.filmorate.dao.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Repository
public class FilmStorageDaoImpl extends DataStorageDaoImpl<Film> implements FilmStorageDao {
    public FilmStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    protected String getInsertQuery() {
        return "INSERT INTO films (name, description, release_date, duration, rating_id) " +
                "VALUES (?,?,?,?,?)";
    }

    protected String getSelectQuery() {
        return "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                "WHERE f.film_id = ?";
    }

    protected String getSelectAllQuery() {
        return "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id ";
    }

    protected String getUpdateQuery() {
        return "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, " +
                "rating_id = ? " +
                "WHERE film_id = ?";
    }

    protected String getDeleteQuery() {
        return "DELETE FROM films WHERE film_id = ?";
    }

    protected RowMapper<Film> getMapper() {
        return new FilmMapper();
    }

    @Override
    public Film create(Film film) {
        jdbcTemplate.update(getInsertQuery(), film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId());

        return jdbcTemplate.queryForObject("SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                "ORDER BY film_id DESC LIMIT 1", getMapper());
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update(getUpdateQuery(), film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId());

        return read(film.getId());
    }

    @Override
    public Collection<Film> readAllSortedByYear(long directorId) {
        String query = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.mpa_name " +
                "FROM films_directors AS fd " +
                "LEFT OUTER JOIN films AS f ON fd.film_id = f.film_id " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                "WHERE director_id = " + directorId +
                "ORDER BY f.release_date";

        return jdbcTemplate.query(query, getMapper());
    }

    @Override
    public Collection<Film> readAllSortedByLikes(long directorId) {
        String query = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.mpa_name " +
                "FROM films_directors AS fd " +
                "LEFT OUTER JOIN (SELECT film_id, COUNT (user_id) AS like_count " +
                "FROM films_likes " +
                "GROUP BY film_id) AS l ON fd.film_id = l.film_id " +
                "LEFT OUTER JOIN films AS f ON fd.film_id = f.film_id " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                "ORDER BY like_count DESC";

        return jdbcTemplate.query(query, getMapper());
    }

    @Override
    public Collection<Film> searchFilms(String query, String by) {
        String sqlCondition = getSqlCondition(query, by);

        String sqlSearchFilms = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, " +
                "f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN (SELECT film_id, COUNT (user_id) AS like_count " +
                "FROM films_likes " +
                "GROUP BY film_id) AS l ON f.film_id = l.film_id " +
                "LEFT OUTER JOIN films_directors AS fd ON f.film_id = fd.film_id " +
                "LEFT OUTER JOIN directors AS d ON fd.director_id = d.director_id " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " + sqlCondition +
                "ORDER BY like_count DESC";

        return jdbcTemplate.query(sqlSearchFilms, getMapper());
    }

    private static String getSqlCondition(String query, String by) {
        String sqlCondition = null;

        if (by.equals("director")) {
            sqlCondition = "WHERE LOWER(d.director_name) LIKE LOWER('%" + query + "%') ";
        }

        if (by.equals("title")) {
            sqlCondition = "WHERE LOWER(f.name) LIKE LOWER('%" + query + "%') ";
        }

        if (by.equals("director,title") || by.equals("title,director")) {
            sqlCondition = "WHERE LOWER(f.name) LIKE LOWER('%" + query + "%') " +
                    "OR LOWER(d.director_name) LIKE LOWER('%" + query + "%') ";
        }

        return sqlCondition;
    }
}