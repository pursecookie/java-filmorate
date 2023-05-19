package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Создан фильм {}", film);
        return filmService.create(film);
    }

    @PutMapping
    public Film put(@RequestBody Film film) {
        log.info("Фильм {} обновлен", film);
        return filmService.put(film);
    }

    @GetMapping("/{id}")
    public Film find(@PathVariable long id) {
        return filmService.find(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        log.info("Добавлен лайк к фильму {} от пользователя {}", filmService.find(id), filmService.find(userId));
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable long id, @PathVariable long userId) {
        log.info("У фильма {} удален лайк от пользователя {}", filmService.find(id), filmService.find(userId));
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> findPopularFilms(@RequestParam(required = false) String count) {
        return filmService.findPopularFilms(count);
    }

}
