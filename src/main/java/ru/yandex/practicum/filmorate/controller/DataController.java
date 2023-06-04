package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.Collection;

public interface DataController<T extends StorageData> {
    T create(T data);

    T read(long id);

    Collection<T> readAll();

    T update(T data);

    void delete(long id);
}
