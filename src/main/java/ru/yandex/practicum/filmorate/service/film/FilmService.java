package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.DataService;

import java.util.Collection;

public interface FilmService extends DataService<Film> {
    Collection<Film> readAllSortedFilmsByDirector(long directorId, String sortBy);
}