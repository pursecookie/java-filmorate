package ru.yandex.practicum.filmorate.dao.film;

import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Set;

public interface FilmStorageDao extends DataStorageDao<Film> {
    void addGenres(long filmId, Collection<Genre> genres);

    Set<Genre> getGenres(long filmId);

    void updateGenres(long filmId, Set<Genre> genres);
}
