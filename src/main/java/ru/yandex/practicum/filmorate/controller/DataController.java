package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.StorageData;
import ru.yandex.practicum.filmorate.service.DataService;

import javax.validation.Valid;
import java.util.Collection;

public abstract class DataController<T extends StorageData> {
    protected final DataService<T> dataService;

    @Autowired
    public DataController(DataService<T> dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public T create(@Valid @RequestBody T data) {
        return dataService.create(data);
    }

    @GetMapping("/{id}")
    public T read(@PathVariable long id) {
        return dataService.read(id);
    }

    @GetMapping
    public Collection<T> readAll() {
        return dataService.readAll();
    }

    @PutMapping
    public T update(@RequestBody T data) {
        return dataService.update(data);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        dataService.delete(id);
    }
}
