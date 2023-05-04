package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<T> {
    Collection<T> findAll();

    T find(int id);

    T create(T object);

    T put(T object);
}
