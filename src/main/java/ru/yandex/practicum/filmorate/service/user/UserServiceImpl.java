package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.component.DataFinder;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.dao.director.DirectorStorageDao;
import ru.yandex.practicum.filmorate.dao.feed.FeedStorageDao;
import ru.yandex.practicum.filmorate.dao.friendship.FriendshipStorageDao;
import ru.yandex.practicum.filmorate.dao.genre.GenreStorageDao;
import ru.yandex.practicum.filmorate.dao.like.LikeStorageDao;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

import java.util.Collection;

@Service
public class UserServiceImpl extends DataServiceImpl<User> implements UserService {
    private final FeedStorageDao feedStorageDao;
    private final FriendshipStorageDao friendshipStorageDao;
    private final LikeStorageDao likeStorageDao;
    private final GenreStorageDao genreStorageDao;
    private final DirectorStorageDao directorStorageDao;

    @Autowired
    protected UserServiceImpl(DataStorageDao<User> dataStorageDao, DataFinder dataFinder,
                              FeedStorageDao feedStorageDao,
                              FriendshipStorageDao friendshipStorageDao,
                              LikeStorageDao likeStorageDao,
                              GenreStorageDao genreStorageDao,
                              DirectorStorageDao directorStorageDao) {
        super(dataStorageDao, dataFinder);
        this.feedStorageDao = feedStorageDao;
        this.friendshipStorageDao = friendshipStorageDao;
        this.likeStorageDao = likeStorageDao;
        this.genreStorageDao = genreStorageDao;
        this.directorStorageDao = directorStorageDao;
    }

    @Override
    public void createFriendship(long userFrom, long userTo) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), userFrom);
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), userTo);
        friendshipStorageDao.create(userFrom, userTo);
        feedStorageDao.create(userFrom, userTo, "FRIEND", "ADD");
    }

    @Override
    public Collection<User> readAllFriendships(long userId) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), userId);

        return friendshipStorageDao.readAll(userId);
    }

    @Override
    public Collection<User> readCommonFriends(long userId, long friendId) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), userId);
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), friendId);

        return friendshipStorageDao.readCommon(userId, friendId);
    }

    @Override
    public void deleteFriendship(long userId, long friendId) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), userId);
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), friendId);
        feedStorageDao.create(userId, friendId, "FRIEND", "REMOVE");
        friendshipStorageDao.delete(userId, friendId);
    }

    @Override
    public Collection<Event> readUserFeed(long userId) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), userId);

        return feedStorageDao.readUserFeed(userId);
    }

    @Override
    public Collection<Film> readFilmRecommendations(long userId) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), userId);

        Collection<Film> filmRecommendations = likeStorageDao.readFilmRecommendations(userId);

        for (Film film : filmRecommendations) {
            film.setGenres(genreStorageDao.getGenresForFilms(film.getId()));
            film.setDirectors(directorStorageDao.getDirectorsForFilms(film.getId()));
        }

        return filmRecommendations;
    }
}
