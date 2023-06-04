package ru.yandex.practicum.filmorate.service.friendship;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendshipService {
    void create(long userTo, long userFrom);

    Collection<User> readAll(long userId);

    Collection<User> readCommon(long userId, long friendId);

    void delete(long userId, long friendId);
}
