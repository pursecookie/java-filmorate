package ru.yandex.practicum.filmorate.dao.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;

@Repository
public class FilmStorageDaoImpl extends DataStorageDaoImpl<Film> implements FilmStorageDao {
    public FilmStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    protected String getInsertQuery() {
        return "INSERT INTO films (name, description, release_date, duration, rating_id) VALUES (?,?,?,?,?)";
    }

    protected String getSelectQuery() {
        return "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id " +
                "WHERE f.film_id = ?";
    }

    protected String getSelectAllQuery() {
        return "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.mpa_name " +
                "FROM films AS f " +
                "LEFT OUTER JOIN ratings AS r ON f.rating_id = r.rating_id";
    }

    protected String getUpdateQuery() {
        return "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, rating_id = ? " +
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

        return jdbcTemplate.queryForObject("SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.mpa_name " +
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
}