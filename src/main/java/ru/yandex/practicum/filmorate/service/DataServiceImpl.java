package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.component.DataFinder;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.Collection;

@Service
public abstract class DataServiceImpl<T extends StorageData> implements DataService<T> {
    protected final DataStorageDao<T> dataStorageDao;
    protected final DataFinder dataFinder;

    @Autowired
    protected DataServiceImpl(DataStorageDao<T> dataStorageDao, DataFinder dataFinder) {
        this.dataStorageDao = dataStorageDao;
        this.dataFinder = dataFinder;
    }

    @Override
    public T create(T data) {
        return dataStorageDao.create(data);
    }

    @Override
    public T read(long id) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), id);

        return dataStorageDao.read(id);
    }

    @Override
    public Collection<T> readAll() {
        return dataStorageDao.readAll();
    }

    @Override
    public T update(T data) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), data.getId());

        return dataStorageDao.update(data);
    }

    @Override
    public void delete(long id) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), id);
        dataStorageDao.delete(id);
    }
}
