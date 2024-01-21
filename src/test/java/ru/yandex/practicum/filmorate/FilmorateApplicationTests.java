package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.user.UserStorageDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final UserStorageDao userStorage;
    private final FilmService filmService;

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

    @DisplayName("Проверка создания фильма с тремя жанрами")
    @Test
    public void shouldCreateFilmWithThreeGenres() {
        Film testFilm = new Film();
        Set<Genre> genres = new HashSet<>();

        genres.add(new Genre(1, "Комедия"));
        genres.add(new Genre(2, "Драма"));
        genres.add(new Genre(3, "Мультфильм"));

        Mpa mpa = new Mpa(1, "G");

        testFilm.setName("New film");
        testFilm.setReleaseDate(LocalDate.of(1999, 4, 30));
        testFilm.setDescription("New film to delete");
        testFilm.setDuration(20);
        testFilm.setMpa(mpa);
        testFilm.setGenres(genres);

        Film newFilm = filmService.create(testFilm);

        assertEquals(3, newFilm.getGenres().size());

        Film readFilm = filmService.read(newFilm.getId());

        assertEquals(3, readFilm.getGenres().size());
    }

}
