package ru.yandex.practicum.filmorate.service.like;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface LikeService {
    void create(long filmId, long userId);

    Collection<Film> readPopular(String count);

    void delete(long filmId, long userId);
}
