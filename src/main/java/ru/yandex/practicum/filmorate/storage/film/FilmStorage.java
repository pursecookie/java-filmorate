package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

public interface FilmStorage extends Storage<Film> {

    Collection<Film> findAll();

    Film find(int id);

    Film create(Film film);

    Film put(Film film);
}
