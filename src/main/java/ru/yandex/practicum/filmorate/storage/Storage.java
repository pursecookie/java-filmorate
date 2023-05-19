package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.Collection;

public interface Storage<T extends StorageData> {
    Collection<T> findAll();

    T find(long id);

    T create(T object);

    T put(T object);
}
