package ru.yandex.practicum.filmorate.dao.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;

@Repository
public class GenreStorageDaoImpl extends DataStorageDaoImpl<Genre> implements GenreStorageDao {
    public GenreStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    protected String getInsertQuery() {
        return "INSERT INTO genres (genre_name) VALUES (?)";
    }

    protected String getSelectQuery() {
        return "SELECT * FROM genres WHERE genre_id = ?";
    }

    protected String getSelectAllQuery() {
        return "SELECT * FROM genres";
    }

    protected String getUpdateQuery() {
        return "UPDATE genres SET genre_name = ? WHERE genre_id = ?";
    }

    protected String getDeleteQuery() {
        return "DELETE FROM genres WHERE genre_id = ?";
    }

    protected RowMapper<Genre> getMapper() {
        return new GenreMapper();
    }

    @Override
    public Genre create(Genre genre) {
        jdbcTemplate.update(getInsertQuery(), genre.getName());

        return jdbcTemplate.queryForObject(getSelectAllQuery() + " WHERE genre_name = ?",
                getMapper(), genre.getName());
    }

    @Override
    public Genre update(Genre genre) {
        jdbcTemplate.update(getUpdateQuery(), genre.getName(), genre.getId());

        return read(genre.getId());
    }
}