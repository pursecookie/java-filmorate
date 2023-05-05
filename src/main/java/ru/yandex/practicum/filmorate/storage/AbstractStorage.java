package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStorage<T> implements Storage<T> {
    protected final Map<Integer, T> storage = new HashMap<>();

    @Override
    public Collection<T> findAll() {
        return storage.values();
    }

    @Override
    public T find(int id) {
        if (!storage.containsKey(id)) {
            throw new NotFoundException("Указанный id " + id + " не найден");
        }
        return storage.get(id);
    }

    @Override
    public abstract T create(T data);

    @Override
    public abstract T put(T data);

}
