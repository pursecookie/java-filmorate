package ru.yandex.practicum.filmorate.dao.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

@Repository
public class MpaStorageDaoImpl extends DataStorageDaoImpl<Mpa> implements MpaStorageDao {
    public MpaStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    protected String getInsertQuery() {
        return "INSERT INTO ratings (mpa_name) VALUES (?)";
    }

    protected String getSelectQuery() {
        return "SELECT rating_id, mpa_name FROM ratings WHERE rating_id = ?";
    }

    protected String getSelectAllQuery() {
        return "SELECT rating_id AS mpa, mpa_name FROM ratings";
    }

    protected String getUpdateQuery() {
        return "UPDATE ratings SET mpa_name = ? WHERE rating_id = ?";
    }

    protected String getDeleteQuery() {
        return "DELETE FROM ratings WHERE rating_id = ?";
    }

    protected RowMapper<Mpa> getMapper() {
        return new MpaMapper();
    }

    @Override
    public Mpa create(Mpa mpa) {
        jdbcTemplate.update(getInsertQuery(), mpa.getName());

        return jdbcTemplate.queryForObject(getSelectAllQuery() + " WHERE mpa_name = ?", getMapper(), mpa.getName());
    }

    @Override
    public Mpa update(Mpa mpa) {
        jdbcTemplate.update(getUpdateQuery(), mpa.getName(), mpa.getId());

        return read(mpa.getId());
    }
}