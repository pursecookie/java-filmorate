package ru.yandex.practicum.filmorate.dao.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

        return jdbcTemplate.queryForObject(getSelectAllQuery() + " WHERE f.name = ?", getMapper(), film.getName());
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update(getUpdateQuery(), film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId());

        return read(film.getId());
    }

    public void addGenres(long filmId, Collection<Genre> genres) {
        for (Genre genre : genres) {
            jdbcTemplate.update("INSERT INTO films_genres (film_id, genre_id) VALUES (?,?)", filmId, genre.getId());
        }
    }

    public Set<Genre> getGenres(long filmId) {
        return new HashSet<>(jdbcTemplate.query("SELECT fg.genre_id, g.genre_name " +
                "FROM films_genres AS fg " +
                "LEFT OUTER JOIN genres AS g ON fg.genre_id = g.genre_id " +
                "WHERE fg.film_id = ?" +
                "ORDER BY fg.genre_id", new GenreMapper(), filmId));
    }

    public void updateGenres(long filmId, Set<Genre> genres) {
        jdbcTemplate.update("DELETE FROM films_genres WHERE film_id = ?", filmId);
        addGenres(filmId, genres);
    }

}