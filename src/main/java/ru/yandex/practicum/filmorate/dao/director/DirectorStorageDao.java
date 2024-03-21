package ru.yandex.practicum.filmorate.dao.director;

import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.Set;

public interface DirectorStorageDao extends DataStorageDao<Director> {
    void addDirectorsForFilms(long filmId, Set<Director> directors);

    Set<Director> getDirectorsForFilms(long filmId);

    void updateDirectorsForFilms(long filmId, Set<Director> directors);
}