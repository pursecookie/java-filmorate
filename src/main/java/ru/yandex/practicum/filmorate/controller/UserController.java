package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    Counter counter = new Counter();
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        user.setId(counter.count());
        users.put(user.getId(), user);
        log.info("Создан пользователь {}", user);
        return user;
    }

    @PutMapping
    public User put(@RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new RuntimeException();
        }
        users.put(user.getId(), user);
        log.info("Пользователь {} обновлен", user);
        return user;
    }
}
