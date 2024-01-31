package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.DataService;

import java.util.Collection;

public interface FilmService extends DataService<Film> {
    Collection<Film> readAllSortedFilmsByDirector(long directorId, String sortBy);

    void createLike(long filmId, long userId);

    Collection<Film> readPopularFilms(Long count, Long genreId, Integer year);

    void deleteLike(long filmId, long userId);

    Collection<Film> searchFilms(String query, String by);

    Collection<Film> readCommonFilms(long userId, long friendId);
}