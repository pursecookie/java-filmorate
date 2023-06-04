package ru.yandex.practicum.filmorate.service.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.dao.like.LikeStorageDaoImpl;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Service
public class LikeServiceImpl implements LikeService {

    LikeStorageDaoImpl likeStorageDaoImpl;

    @Autowired
    public LikeServiceImpl(LikeStorageDaoImpl likeStorageDaoImpl) {
        this.likeStorageDaoImpl = likeStorageDaoImpl;
    }

    @Override
    public void create(long filmId, long userId) {
        likeStorageDaoImpl.create(filmId, userId);
    }

    @Override
    public Collection<Film> readPopular(String count) {
        return likeStorageDaoImpl.readPopular(count);
    }

    @Override
    public void delete(long filmId, long userId) {
        if (likeStorageDaoImpl.isExists(userId)) {
            likeStorageDaoImpl.delete(filmId, userId);
        } else {
            throw new NotFoundException("Данные с id " + userId + " не найдены");
        }
    }
}
