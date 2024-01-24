package ru.yandex.practicum.filmorate.dao.director;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.mapper.DirectorMapper;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.HashSet;
import java.util.Set;

@Repository
public class DirectorStorageDaoImpl extends DataStorageDaoImpl<Director> implements DirectorStorageDao {
    public DirectorStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    protected String getInsertQuery() {
        return "INSERT INTO directors (director_name) VALUES (?)";
    }

    protected String getSelectQuery() {
        return "SELECT director_id, director_name FROM directors WHERE director_id = ?";
    }

    protected String getSelectAllQuery() {
        return "SELECT director_id, director_name FROM directors";
    }

    protected String getUpdateQuery() {
        return "UPDATE directors SET director_name = ? WHERE director_id = ?";
    }

    protected String getDeleteQuery() {
        return "DELETE FROM directors WHERE director_id = ?";
    }

    protected RowMapper<Director> getMapper() {
        return new DirectorMapper();
    }

    @Override
    public Director create(Director director) {
        jdbcTemplate.update(getInsertQuery(), director.getName());

        return jdbcTemplate.queryForObject(getSelectAllQuery() + " WHERE director_name = ?",
                getMapper(), director.getName());
    }

    @Override
    public Director update(Director director) {
        jdbcTemplate.update(getUpdateQuery(), director.getName(), director.getId());

        return read(director.getId());
    }

    @Override
    public void addDirectorsForFilms(long filmId, Set<Director> directors) {
        for (Director director : directors) {
            jdbcTemplate.update("INSERT INTO films_directors (film_id, director_id) VALUES (?,?)",
                    filmId, director.getId());
        }
    }

    @Override
    public Set<Director> getDirectorsForFilms(long filmId) {
        return new HashSet<>(jdbcTemplate.query("SELECT fd.director_id, d.director_name " +
                "FROM films_directors AS fd " +
                "LEFT OUTER JOIN directors AS d ON fd.director_id = d.director_id " +
                "WHERE fd.film_id = ?" +
                "ORDER BY fd.director_id", new DirectorMapper(), filmId));
    }

    @Override
    public void updateDirectorsForFilms(long filmId, Set<Director> directors) {
        jdbcTemplate.update("DELETE FROM films_directors WHERE film_id = ?", filmId);
        addDirectorsForFilms(filmId, directors);
    }
}