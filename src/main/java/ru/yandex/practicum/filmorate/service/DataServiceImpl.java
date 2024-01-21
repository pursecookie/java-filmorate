package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.Collection;

@Service
public abstract class DataServiceImpl<T extends StorageData> implements DataService<T> {
    protected final DataStorageDao<T> dataStorageDao;

    @Autowired
    protected DataServiceImpl(DataStorageDao<T> dataStorageDao) {
        this.dataStorageDao = dataStorageDao;
    }

    @Override
    public T create(T data) {
        return dataStorageDao.create(data);
    }

    @Override
    public T read(long id) {
        if (dataStorageDao.isExists(id)) {
            return dataStorageDao.read(id);
        } else {
            throw new NotFoundException("Данные с id " + id + " не найдены");
        }
    }

    @Override
    public Collection<T> readAll() {
        return dataStorageDao.readAll();
    }

    @Override
    public T update(T data) {
        if (dataStorageDao.isExists(data.getId())) {
            return dataStorageDao.update(data);
        } else {
            throw new NotFoundException("Данные с id " + data.getId() + " не найдены");
        }
    }

    @Override
    public void delete(long id) {
        if (dataStorageDao.isExists(id)) {
            dataStorageDao.delete(id);
        } else {
            throw new NotFoundException("Данные с id " + id + " не найдены");
        }
    }
}
