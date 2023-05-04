package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {
    @DisplayName("Проверка создания пользователя с пустым именем")
    @Test
    public void shouldSetLoginAsNameIfNameIsEmpty() {
        User testUser = new User();

        testUser.setLogin("cookie");
        testUser.setEmail("pursecookie@yandex.ru");
        testUser.setBirthday(LocalDate.of(1997, 1, 25));

        UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
        User newUser = userController.create(testUser);

        assertEquals("cookie", newUser.getName(), "Логин должен быть в качестве имени пользователя");
    }
}
