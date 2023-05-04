package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

public interface UserStorage extends Storage<User> {
    Collection<User> findAll();

    User find(int id);

    User create(User user);

    User put(User user);
}
