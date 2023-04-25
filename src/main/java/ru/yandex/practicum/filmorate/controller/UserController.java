package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Counter;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* Патимат, здравствуйте! У меня есть два вопроса по ТЗ, напишу их здесь.
1. Правильно ли я понимаю, что, используя аннотацию @Slf4j, мне не нужно дополнительно прописывать логи?
И я не поняла, как мне логировать причины того, что валидация не пройдена, если я использую аннотации ограничения?
2. Если я выполняю задание со звёздочкой, нужно ли мне писать свои JUnit тесты для валидации?
Насколько мне известно, тестирование аннотаций Spring у нас будет в дальнейшем, и на данном этапе достаточно пройти
тесты в Postman? И получается нам не нужно писать свое кастомное исключение? */

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
        return user;
    }

    @PutMapping
    public User put(@RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new RuntimeException();
        }
        users.put(user.getId(), user);
        return user;
    }
}
