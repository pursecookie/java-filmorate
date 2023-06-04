package ru.yandex.practicum.filmorate.service.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.dao.friendship.FriendshipStorageDaoImpl;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    FriendshipStorageDaoImpl friendshipStorageDaoImpl;

    @Autowired
    public FriendshipServiceImpl(FriendshipStorageDaoImpl friendshipStorageDaoImpl) {
        this.friendshipStorageDaoImpl = friendshipStorageDaoImpl;
    }

    @Override
    public void create(long userFrom, long userTo) {
        if (friendshipStorageDaoImpl.isExists(userTo)) {
            friendshipStorageDaoImpl.create(userFrom, userTo);
        } else {
            throw new NotFoundException("Данные с id " + userTo + " не найдены");
        }
    }

    @Override
    public Collection<User> readAll(long userId) {
        return friendshipStorageDaoImpl.readAll(userId);
    }

    @Override
    public Collection<User> readCommon(long userId, long friendId) {
        return friendshipStorageDaoImpl.readCommon(userId, friendId);
    }

    @Override
    public void delete(long userId, long friendId) {
        friendshipStorageDaoImpl.delete(userId, friendId);
    }
}
