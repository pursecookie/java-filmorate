package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    Counter counter = new Counter();
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        film.setId(counter.count());
        films.put(film.getId(), film);
        log.info("Создан фильм {}", film);
        return film;
    }

    @PutMapping
    public Film put(@RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new RuntimeException();
        }
        films.put(film.getId(), film);
        log.info("Фильм {} обновлен", film);
        return film;
    }
}
