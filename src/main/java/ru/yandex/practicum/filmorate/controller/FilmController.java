package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.DataService;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController extends DataController<Film> {
    private final FilmService filmService;

    @Autowired
    public FilmController(@Qualifier("filmServiceImpl") DataService<Film> dataService, FilmService filmService) {
        super(dataService);
        this.filmService = filmService;
    }

    @GetMapping("/director/{directorId}")
    public Collection<Film> readAllSortedFilmsByDirector(@PathVariable long directorId,
                                                         @RequestParam String sortBy) {
        return filmService.readAllSortedFilmsByDirector(directorId, sortBy);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void createLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.createLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> readPopularFilms(@RequestParam(required = false) String count) {
        return filmService.readPopularFilms(count);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.deleteLike(filmId, userId);
    }
}