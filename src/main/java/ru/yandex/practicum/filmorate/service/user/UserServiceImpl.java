package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.dao.feed.FeedStorageDao;
import ru.yandex.practicum.filmorate.dao.friendship.FriendshipStorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

import java.util.Collection;

@Service
public class UserServiceImpl extends DataServiceImpl<User> implements UserService {
    private final FeedStorageDao feedStorageDao;
    private final FriendshipStorageDao friendshipStorageDao;

    @Autowired
    protected UserServiceImpl(DataStorageDao<User> dataStorageDao,
                              FeedStorageDao feedStorageDao,
                              FriendshipStorageDao friendshipStorageDao) {
        super(dataStorageDao);
        this.feedStorageDao = feedStorageDao;
        this.friendshipStorageDao = friendshipStorageDao;
    }

    @Override
    public void createFriendship(long userFrom, long userTo) {
        if (friendshipStorageDao.isExists(userTo)) {
            friendshipStorageDao.create(userFrom, userTo);
            feedStorageDao.create(userFrom, userTo, "FRIEND", "ADD");
        } else {
            throw new NotFoundException("Данные с id " + userTo + " не найдены");
        }
    }

    @Override
    public Collection<User> readAllFriendships(long userId) {
        if (friendshipStorageDao.isExists(userId)) {
            return friendshipStorageDao.readAll(userId);
        } else {
            throw new NotFoundException("Данные с id " + userId + " не найдены");
        }
    }

    @Override
    public Collection<User> readCommonFriends(long userId, long friendId) {
        return friendshipStorageDao.readCommon(userId, friendId);
    }

    @Override
    public void deleteFriendship(long userId, long friendId) {
        feedStorageDao.create(userId, friendId, "FRIEND", "REMOVE");
        friendshipStorageDao.delete(userId, friendId);
    }

    @Override
    public Collection<Event> readUserFeed(long userId) {
        if (dataStorageDao.isExists(userId)) {
            return feedStorageDao.readUserFeed(userId);
        } else {
            throw new NotFoundException("Данные с id " + userId + " не найдены");
        }
    }
}
