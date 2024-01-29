package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.dao.director.DirectorStorageDao;
import ru.yandex.practicum.filmorate.dao.feed.FeedStorageDao;
import ru.yandex.practicum.filmorate.dao.film.FilmStorageDao;
import ru.yandex.practicum.filmorate.dao.genre.GenreStorageDao;
import ru.yandex.practicum.filmorate.dao.like.LikeStorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FilmServiceImpl extends DataServiceImpl<Film> implements FilmService {
    private final GenreStorageDao genreStorageDao;
    private final DirectorStorageDao directorStorageDao;
    private final FilmStorageDao filmStorageDao;
    private final LikeStorageDao likeStorageDao;
    private final FeedStorageDao feedStorageDao;

    @Autowired
    public FilmServiceImpl(DataStorageDao<Film> dataStorageDao,
                           GenreStorageDao genreStorageDao,
                           DirectorStorageDao directorStorageDao,
                           FilmStorageDao filmStorageDao,
                           LikeStorageDao likeStorageDao,
                           FeedStorageDao feedStorageDao) {
        super(dataStorageDao);
        this.genreStorageDao = genreStorageDao;
        this.directorStorageDao = directorStorageDao;
        this.filmStorageDao = filmStorageDao;
        this.likeStorageDao = likeStorageDao;
        this.feedStorageDao = feedStorageDao;
    }

    @Override
    public Film create(Film film) {
        Film result = dataStorageDao.create(film);

        genreStorageDao.addGenresForFilms(result.getId(), film.getGenres());
        directorStorageDao.addDirectorsForFilms(result.getId(), film.getDirectors());
        result.setGenres(genreStorageDao.getGenresForFilms(result.getId()));
        result.setDirectors(directorStorageDao.getDirectorsForFilms(result.getId()));

        return result;
    }

    @Override
    public Film read(long filmId) {
        if (dataStorageDao.isExists(filmId)) {
            Film result = dataStorageDao.read(filmId);

            result.setGenres(genreStorageDao.getGenresForFilms(result.getId()));
            result.setDirectors(directorStorageDao.getDirectorsForFilms(result.getId()));

            return result;
        } else {
            throw new NotFoundException("Данные с id " + filmId + " не найдены");
        }
    }

    @Override
    public Collection<Film> readAll() {
        Collection<Film> films = dataStorageDao.readAll();

        for (Film film : films) {
            film.setGenres(genreStorageDao.getGenresForFilms(film.getId()));
            film.setDirectors(directorStorageDao.getDirectorsForFilms(film.getId()));
        }

        return films;
    }

    @Override
    public Film update(Film film) {
        if (dataStorageDao.isExists(film.getId())) {
            Film result = dataStorageDao.update(film);

            genreStorageDao.updateGenresForFilms(result.getId(), film.getGenres());
            directorStorageDao.updateDirectorsForFilms(result.getId(), film.getDirectors());
            result.setGenres(genreStorageDao.getGenresForFilms(result.getId()));
            result.setDirectors(directorStorageDao.getDirectorsForFilms(result.getId()));

            return result;
        } else {
            throw new NotFoundException("Данные с id " + film.getId() + " не найдены");
        }
    }

    @Override
    public Collection<Film> readAllSortedFilmsByDirector(long directorId, String sortBy) {
        if (!directorStorageDao.isExists(directorId)) {
            throw new NotFoundException("Режиссёр с id " + directorId + " не найден");
        }

        Collection<Film> sortedFilms = new ArrayList<>();

        if (sortBy.equals("year")) {
            sortedFilms = filmStorageDao.readAllSortedByYear(directorId);
        }

        if (sortBy.equals("likes")) {
            sortedFilms = filmStorageDao.readAllSortedByLikes(directorId);
        }

        for (Film film : sortedFilms) {
            film.setGenres(genreStorageDao.getGenresForFilms(film.getId()));
            film.setDirectors(directorStorageDao.getDirectorsForFilms(film.getId()));
        }

        return sortedFilms;
    }

    @Override
    public void createLike(long filmId, long userId) {
        likeStorageDao.create(filmId, userId);
        feedStorageDao.create(userId, filmId, "LIKE", "ADD");
    }

    @Override
    public Collection<Film> readPopularFilms(String count) {
        Collection<Film> popularFilms = likeStorageDao.readPopular(count);

        for (Film film : popularFilms) {
            film.setGenres(genreStorageDao.getGenresForFilms(film.getId()));
        }

        return popularFilms;
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        if (likeStorageDao.isExists(userId)) {
            likeStorageDao.delete(filmId, userId);
            feedStorageDao.create(userId, filmId, "LIKE", "REMOVE");
        } else {
            throw new NotFoundException("Данные с id " + userId + " не найдены");
        }
    }
}