package ru.yandex.practicum.filmorate.dao.genre;

import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Set;

public interface GenreStorageDao extends DataStorageDao<Genre> {
    void addGenresForFilms(long filmId, Set<Genre> genres);

    Set<Genre> getGenresForFilms(long filmId);

    void updateGenresForFilms(long filmId, Set<Genre> genres);
}
