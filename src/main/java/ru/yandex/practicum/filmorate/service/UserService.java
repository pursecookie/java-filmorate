package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User put(User user) {
        return userStorage.put(user);
    }

    public User find(int id) {
        return userStorage.find(id);
    }

    public void addFriend(int id, int friendId) {
        find(id).addFriend(friendId);
        find(friendId).addFriend(id);
    }

    public void removeFriend(int id, int friendId) {
        find(id).removeFriend(friendId);
        find(friendId).removeFriend(id);
    }

    public Collection<User> findAllFriends(int id) {
        return find(id).getFriends().stream()
                .map(this::find)
                .collect(Collectors.toList());
    }

    public Collection<User> findCommonFriends(int id, int otherId) {
        return find(id).getFriends().stream()
                .filter(find(otherId).getFriends()::contains)
                .map(this::find)
                .collect(Collectors.toList());
    }
}
