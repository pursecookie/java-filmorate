package ru.yandex.practicum.filmorate.service.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.genre.GenreStorageDao;
import ru.yandex.practicum.filmorate.dao.like.LikeStorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Service
public class LikeService {
    private final LikeStorageDao likeStorageDao;
    private final GenreStorageDao genreStorageDao;

    @Autowired
    public LikeService(LikeStorageDao likeStorageDao, GenreStorageDao genreStorageDao) {
        this.likeStorageDao = likeStorageDao;
        this.genreStorageDao = genreStorageDao;
    }

    public void create(long filmId, long userId) {
        likeStorageDao.create(filmId, userId);
    }

    public Collection<Film> readPopular(String count) {
        Collection<Film> popularFilms = likeStorageDao.readPopular(count);

        for (Film film : popularFilms) {
            film.setGenres(genreStorageDao.getGenresForFilms(film.getId()));
        }

        return popularFilms;
    }

    public void delete(long filmId, long userId) {
        if (likeStorageDao.isExists(userId)) {
            likeStorageDao.delete(filmId, userId);
        } else {
            throw new NotFoundException("Данные с id # " + userId + " не найдены");
        }
    }
}