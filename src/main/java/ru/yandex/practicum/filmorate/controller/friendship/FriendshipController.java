package ru.yandex.practicum.filmorate.controller.friendship;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendshipController {
    void create(long userTo, long userFrom);

    Collection<User> readAll(long userId);

    Collection<User> readCommon(long userId, long otherId);

    void delete(long userId, long friendId);
}
