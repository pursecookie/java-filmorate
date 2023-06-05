package ru.yandex.practicum.filmorate.service.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.dao.like.LikeStorageDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Service
public class LikeService {

    LikeStorageDao likeStorageDao;

    @Autowired
    public LikeService(LikeStorageDao likeStorageDao) {
        this.likeStorageDao = likeStorageDao;
    }

    public void create(long filmId, long userId) {
        likeStorageDao.create(filmId, userId);
    }

    public Collection<Film> readPopular(String count) {
        return likeStorageDao.readPopular(count);
    }

    public void delete(long filmId, long userId) {
        if (likeStorageDao.isExists(userId)) {
            likeStorageDao.delete(filmId, userId);
        } else {
            throw new NotFoundException("Данные с id " + userId + " не найдены");
        }
    }
}
