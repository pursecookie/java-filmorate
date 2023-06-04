package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.Collection;

public interface DataService<T extends StorageData> {
    T create(T data);

    T read(long id);

    Collection<T> readAll();

    T update(T data);

    void delete(long id);
}
