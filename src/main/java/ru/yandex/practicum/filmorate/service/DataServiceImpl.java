package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.Collection;

public abstract class DataServiceImpl<T extends StorageData> implements DataService<T> {
    protected final DataStorageDaoImpl<T> dataStorageDaoImpl;

    @Autowired
    protected DataServiceImpl(DataStorageDaoImpl<T> dataStorageDaoImpl) {
        this.dataStorageDaoImpl = dataStorageDaoImpl;
    }

    @Override
    public T create(T data) {
        return dataStorageDaoImpl.create(data);
    }

    @Override
    public T read(long id) {
        if (dataStorageDaoImpl.isExists(id)) {
            return dataStorageDaoImpl.read(id);
        } else {
            throw new NotFoundException("Данные с id " + id + " не найдены");
        }
    }

    @Override
    public Collection<T> readAll() {
        return dataStorageDaoImpl.readAll();
    }

    @Override
    public T update(T data) {
        if (dataStorageDaoImpl.isExists(data.getId())) {
            return dataStorageDaoImpl.update(data);
        } else {
            throw new NotFoundException("Данные с id " + data.getId() + " не найдены");
        }
    }

    @Override
    public void delete(long id) {
        dataStorageDaoImpl.delete(id);
    }
}
