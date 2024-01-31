package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.DataService;

import java.util.Collection;

public interface UserService extends DataService<User> {
    Collection<Event> readUserFeed(long userId);

    void createFriendship(long userFrom, long userTo);

    Collection<User> readAllFriendships(long userId);

    Collection<User> readCommonFriends(long userId, long friendId);

    void deleteFriendship(long userId, long friendId);

    Collection<Film> readFilmRecommendations(long userId);
}
