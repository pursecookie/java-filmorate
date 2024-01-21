package ru.yandex.practicum.filmorate.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.Collection;

@Repository
public abstract class DataStorageDaoImpl<T extends StorageData> implements DataStorageDao<T> {
    protected final JdbcTemplate jdbcTemplate;

    public DataStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected abstract String getInsertQuery();

    protected abstract String getSelectQuery();

    protected abstract String getSelectAllQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract RowMapper<T> getMapper();

    @Override
    public abstract T create(T data);

    @Override
    public T read(long id) {
        return jdbcTemplate.queryForObject(getSelectQuery(), getMapper(), id);
    }

    @Override
    public Collection<T> readAll() {
        return jdbcTemplate.query(getSelectAllQuery(), getMapper());
    }

    @Override
    public abstract T update(T data);

    @Override
    public void delete(long id) {
        jdbcTemplate.update(getDeleteQuery(), id);
    }

    @Override
    public boolean isExists(long id) {
        try {
            read(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}