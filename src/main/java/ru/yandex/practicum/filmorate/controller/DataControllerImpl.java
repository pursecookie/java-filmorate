package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.StorageData;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

import javax.validation.Valid;
import java.util.Collection;

public class DataControllerImpl<T extends StorageData> implements DataController<T> {
    protected final DataServiceImpl<T> dataServiceImpl;

    @Autowired
    public DataControllerImpl(DataServiceImpl<T> dataServiceImpl) {
        this.dataServiceImpl = dataServiceImpl;
    }

    @Override
    @PostMapping
    public T create(@Valid @RequestBody T data) {
        return dataServiceImpl.create(data);
    }

    @Override
    @GetMapping("/{id}")
    public T read(@PathVariable long id) {
        return dataServiceImpl.read(id);
    }

    @Override
    @GetMapping
    public Collection<T> readAll() {
        return dataServiceImpl.readAll();
    }

    @Override
    @PutMapping
    public T update(@RequestBody T data) {
        return dataServiceImpl.update(data);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        dataServiceImpl.delete(id);
    }
}
