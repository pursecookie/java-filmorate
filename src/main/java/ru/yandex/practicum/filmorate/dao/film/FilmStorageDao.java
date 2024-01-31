package ru.yandex.practicum.filmorate.dao.film;

import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorageDao extends DataStorageDao<Film> {
    Collection<Film> readAllSortedByYear(long directorId);

    Collection<Film> readAllSortedByLikes(long directorId);

    Collection<Film> searchFilms(String query, String by);
}