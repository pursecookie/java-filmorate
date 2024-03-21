package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.component.DataFinder;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.dao.director.DirectorStorageDao;
import ru.yandex.practicum.filmorate.dao.feed.FeedStorageDao;
import ru.yandex.practicum.filmorate.dao.film.FilmStorageDao;
import ru.yandex.practicum.filmorate.dao.genre.GenreStorageDao;
import ru.yandex.practicum.filmorate.dao.like.LikeStorageDao;
import ru.yandex.practicum.filmorate.dao.user.UserStorageDao;
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
    private final UserStorageDao userStorageDao;

    @Autowired
    public FilmServiceImpl(DataStorageDao<Film> dataStorageDao, DataFinder dataFinder,
                           GenreStorageDao genreStorageDao,
                           DirectorStorageDao directorStorageDao,
                           FilmStorageDao filmStorageDao,
                           LikeStorageDao likeStorageDao,
                           FeedStorageDao feedStorageDao, UserStorageDao userStorageDao) {
        super(dataStorageDao, dataFinder);
        this.genreStorageDao = genreStorageDao;
        this.directorStorageDao = directorStorageDao;
        this.filmStorageDao = filmStorageDao;
        this.likeStorageDao = likeStorageDao;
        this.feedStorageDao = feedStorageDao;
        this.userStorageDao = userStorageDao;
    }

    @Override
    public Film create(Film film) {
        Film result = dataStorageDao.create(film);

        genreStorageDao.addGenresForFilms(result.getId(), film.getGenres());
        directorStorageDao.addDirectorsForFilms(result.getId(), film.getDirectors());
        setGenresAndDirectorsForFilms(result);

        return result;
    }

    @Override
    public Film read(long filmId) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), filmId);

        Film result = dataStorageDao.read(filmId);

        setGenresAndDirectorsForFilms(result);

        return result;
    }

    @Override
    public Collection<Film> readAll() {
        Collection<Film> films = dataStorageDao.readAll();
        setGenresAndDirectorsForFilms(films);

        return films;
    }

    @Override
    public Film update(Film film) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), film.getId());

        Film result = dataStorageDao.update(film);

        genreStorageDao.updateGenresForFilms(result.getId(), film.getGenres());
        directorStorageDao.updateDirectorsForFilms(result.getId(), film.getDirectors());
        setGenresAndDirectorsForFilms(result);

        return result;
    }

    @Override
    public Collection<Film> readAllSortedFilmsByDirector(long directorId, String sortBy) {
        dataFinder.checkDataExists(directorStorageDao.getIsExistsQuery(), directorId);

        Collection<Film> sortedFilms = new ArrayList<>();

        if (sortBy.equals("year")) {
            sortedFilms = filmStorageDao.readAllSortedByYear(directorId);
        }

        if (sortBy.equals("likes")) {
            sortedFilms = filmStorageDao.readAllSortedByLikes(directorId);
        }

        setGenresAndDirectorsForFilms(sortedFilms);

        return sortedFilms;
    }

    @Override
    public void createLike(long filmId, long userId) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), filmId);
        dataFinder.checkDataExists(userStorageDao.getIsExistsQuery(), userId);
        likeStorageDao.create(filmId, userId);
        feedStorageDao.create(userId, filmId, "LIKE", "ADD");
    }

    @Override
    public Collection<Film> readPopularFilms(Long count, Long genreId, Integer year) {
        Collection<Film> popularFilms = new ArrayList<>();

        if (genreId == null && year == null) {
            popularFilms = likeStorageDao.readPopular(count);
        }

        if (genreId != null) {
            dataFinder.checkDataExists(genreStorageDao.getIsExistsQuery(), genreId);

            popularFilms = likeStorageDao.readPopularByGenre(count, genreId);
        }

        if (year != null) {
            popularFilms = likeStorageDao.readPopularByYear(count, year);
        }

        if (genreId != null && year != null) {
            dataFinder.checkDataExists(genreStorageDao.getIsExistsQuery(), genreId);

            popularFilms = likeStorageDao.readPopularByGenreAndYear(count, genreId, year);
        }

        setGenresAndDirectorsForFilms(popularFilms);

        return popularFilms;
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), filmId);
        dataFinder.checkDataExists(userStorageDao.getIsExistsQuery(), userId);
        likeStorageDao.delete(filmId, userId);
        feedStorageDao.create(userId, filmId, "LIKE", "REMOVE");
    }

    @Override
    public Collection<Film> searchFilms(String query, String by) {
        Collection<Film> filmsResult = filmStorageDao.searchFilms(query, by);

        setGenresAndDirectorsForFilms(filmsResult);

        return filmsResult;
    }

    @Override
    public Collection<Film> readCommonFilms(long userId, long friendId) {
        dataFinder.checkDataExists(userStorageDao.getIsExistsQuery(), userId);
        dataFinder.checkDataExists(userStorageDao.getIsExistsQuery(), friendId);

        Collection<Film> commonFilms = filmStorageDao.readCommonFilms(userId, friendId);

        setGenresAndDirectorsForFilms(commonFilms);

        return commonFilms;
    }

    private void setGenresAndDirectorsForFilms(Collection<Film> films) {
        films.forEach(film -> {
            film.setGenres(genreStorageDao.getGenresForFilms(film.getId()));
            film.setDirectors(directorStorageDao.getDirectorsForFilms(film.getId()));
        });
    }

    private void setGenresAndDirectorsForFilms(Film film) {
        film.setGenres(genreStorageDao.getGenresForFilms(film.getId()));
        film.setDirectors(directorStorageDao.getDirectorsForFilms(film.getId()));
    }
}