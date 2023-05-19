package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStorage<T extends StorageData> implements Storage<T> {
    protected final Map<Long, T> storage = new HashMap<>();
    private final Counter counter = new Counter();

    @Override
    public Collection<T> findAll() {
        return storage.values();
    }

    @Override
    public T find(long id) {
        if (!storage.containsKey(id)) {
            throw new NotFoundException("Указанный id " + id + " не найден");
        }
        return storage.get(id);
    }

    @Override
    public T create(T data) {
        data.setId(counter.count());
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public T put(T data) {
        if (!storage.containsKey(data.getId())) {
            throw new NotFoundException("Указанный id " + data.getId() + " не найден");
        }
        storage.put(data.getId(), data);
        return data;
    }

}
