package ru.yandex.practicum.filmorate.dao.friendship;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendshipStorageDao {
    void create(long userTo, long userFrom);

    Collection<User> readAll(long userId);

    Collection<User> readCommon(long userId, long friendId);

    void delete(long userId, long friendId);
}
