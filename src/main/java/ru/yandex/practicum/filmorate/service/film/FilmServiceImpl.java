package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.dao.film.FilmStorageDaoImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

import java.util.Collection;

@Service
public class FilmServiceImpl extends DataServiceImpl<Film> implements FilmService {
    private final FilmStorageDaoImpl filmStorageDaoImpl;

    @Autowired
    public FilmServiceImpl(DataStorageDaoImpl<Film> dataStorageDaoImpl, FilmStorageDaoImpl filmStorageDaoImpl) {
        super(dataStorageDaoImpl);
        this.filmStorageDaoImpl = filmStorageDaoImpl;
    }

    @Override
    public Film create(Film film) {
        Film result = dataStorageDaoImpl.create(film);

        filmStorageDaoImpl.addGenres(result.getId(), film.getGenres());
        result.setGenres(filmStorageDaoImpl.getGenres(result.getId()));

        return result;
    }

    @Override
    public Film read(long filmId) {
        if (dataStorageDaoImpl.isExists(filmId)) {
            Film result = dataStorageDaoImpl.read(filmId);

            result.setGenres(filmStorageDaoImpl.getGenres(result.getId()));

            return result;
        } else {
            throw new NotFoundException("Данные с id " + filmId + " не найдены");
        }
    }

    @Override
    public Collection<Film> readAll() {
        Collection<Film> films = dataStorageDaoImpl.readAll();

        for (Film film : films) {
            film.setGenres(filmStorageDaoImpl.getGenres(film.getId()));
        }

        return films;
    }

    @Override
    public Film update(Film film) {
        if (dataStorageDaoImpl.isExists(film.getId())) {
            Film result = dataStorageDaoImpl.update(film);

            filmStorageDaoImpl.updateGenres(result.getId(), film.getGenres());
            result.setGenres(filmStorageDaoImpl.getGenres(result.getId()));

            return result;
        } else {
            throw new NotFoundException("Данные с id " + film.getId() + " не найдены");
        }
    }

}