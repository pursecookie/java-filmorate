package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.Collection;

public interface DataStorageDao<T extends StorageData> {
    T create(T data);

    T read(long id);

    Collection<T> readAll();

    T update(T data);

    void delete(long id);

    boolean isExists(long id);
}
