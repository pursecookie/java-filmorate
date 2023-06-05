package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.dao.film.FilmStorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

import java.util.Collection;

@Service
public class FilmServiceImpl extends DataServiceImpl<Film> implements FilmService {
    private final FilmStorageDao filmStorageDao;

    @Autowired
    public FilmServiceImpl(DataStorageDao<Film> dataStorageDao, FilmStorageDao filmStorageDao) {
        super(dataStorageDao);
        this.filmStorageDao = filmStorageDao;
    }

    @Override
    public Film create(Film film) {
        Film result = dataStorageDao.create(film);

        filmStorageDao.addGenres(result.getId(), film.getGenres());
        result.setGenres(filmStorageDao.getGenres(result.getId()));

        return result;
    }

    @Override
    public Film read(long filmId) {
        if (dataStorageDao.isExists(filmId)) {
            Film result = dataStorageDao.read(filmId);

            result.setGenres(filmStorageDao.getGenres(result.getId()));

            return result;
        } else {
            throw new NotFoundException("Данные с id " + filmId + " не найдены");
        }
    }

    @Override
    public Collection<Film> readAll() {
        Collection<Film> films = dataStorageDao.readAll();

        for (Film film : films) {
            film.setGenres(filmStorageDao.getGenres(film.getId()));
        }

        return films;
    }

    @Override
    public Film update(Film film) {
        if (dataStorageDao.isExists(film.getId())) {
            Film result = dataStorageDao.update(film);

            filmStorageDao.updateGenres(result.getId(), film.getGenres());
            result.setGenres(filmStorageDao.getGenres(result.getId()));

            return result;
        } else {
            throw new NotFoundException("Данные с id " + film.getId() + " не найдены");
        }
    }

}