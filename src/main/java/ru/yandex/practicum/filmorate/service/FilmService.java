package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film put(Film film) {
        return filmStorage.put(film);
    }

    public Film find(long id) {
        return filmStorage.find(id);
    }

    public void addLike(long id, long userId) {
        find(id).addLike(userId);
    }

    public void removeLike(long id, long userId) {
        if (!find(id).getLikes().contains(userId)) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }

        find(id).removeLike(userId);
    }

    public Collection<Film> findPopularFilms(String count) {
        if (count == null) {
            count = String.valueOf(10);
        }

        return findAll().stream()
                .sorted(Comparator.comparingInt((Film o) -> o.getLikes().size()).reversed())
                .limit(Long.parseLong(count))
                .collect(Collectors.toList());
    }

}
