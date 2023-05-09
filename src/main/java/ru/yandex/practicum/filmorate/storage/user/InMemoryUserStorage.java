package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

@Component
public class InMemoryUserStorage extends AbstractStorage<User> implements UserStorage {
    private final Counter counter = new Counter();

    @Override
    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(counter.count());
        storage.put(user.getId(), user);
        return user;
    }
}
