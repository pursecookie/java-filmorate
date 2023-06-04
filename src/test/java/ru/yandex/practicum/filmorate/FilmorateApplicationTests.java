package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.user.UserStorageDaoImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmoRateApplicationTests {
    private final UserStorageDaoImpl userStorage;

    @DisplayName("Проверка создания пользователя с пустым именем")
    @Test
    public void shouldSetLoginAsNameIfNameIsEmpty() {
        User testUser = new User();

        testUser.setLogin("cookie");
        testUser.setEmail("pursecookie@yandex.ru");
        testUser.setBirthday(LocalDate.of(1997, 1, 25));

        User newUser = userStorage.create(testUser);

        assertEquals("cookie", newUser.getName(), "Логин должен быть в качестве имени пользователя");
    }
}
