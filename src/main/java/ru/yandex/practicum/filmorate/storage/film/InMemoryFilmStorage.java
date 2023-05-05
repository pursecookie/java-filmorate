package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

@Component
public class InMemoryFilmStorage extends AbstractStorage<Film> implements FilmStorage {
    private final Counter counter = new Counter();

    @Override
    public Film create(Film film) {
        film.setId(counter.count());
        storage.put(film.getId(), film);
        return film;
    }

    @Override
    public Film put(Film film) {
        if (!storage.containsKey(film.getId())) {
            throw new NotFoundException("Указанный id " + film.getId() + " не найден");
        }
        storage.put(film.getId(), film);
        return film;
    }
}
