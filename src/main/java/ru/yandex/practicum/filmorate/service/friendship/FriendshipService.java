package ru.yandex.practicum.filmorate.service.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.friendship.FriendshipStorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Service
public class FriendshipService {

    FriendshipStorageDao friendshipStorageDao;

    @Autowired
    public FriendshipService(FriendshipStorageDao friendshipStorageDao) {
        this.friendshipStorageDao = friendshipStorageDao;
    }

    public void create(long userFrom, long userTo) {
        if (friendshipStorageDao.isExists(userTo)) {
            friendshipStorageDao.create(userFrom, userTo);
        } else {
            throw new NotFoundException("Данные с id " + userTo + " не найдены");
        }
    }

    public Collection<User> readAll(long userId) {
        if (friendshipStorageDao.isExists(userId)) {
            return friendshipStorageDao.readAll(userId);
        } else {
            throw new NotFoundException("Данные с id " + userId + " не найдены");
        }
    }

    public Collection<User> readCommon(long userId, long friendId) {
        return friendshipStorageDao.readCommon(userId, friendId);
    }

    public void delete(long userId, long friendId) {
        friendshipStorageDao.delete(userId, friendId);
    }
}
