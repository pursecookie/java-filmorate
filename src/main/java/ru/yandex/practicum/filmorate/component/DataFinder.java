package ru.yandex.practicum.filmorate.component;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

@Component
@RequiredArgsConstructor
public class DataFinder {
    private final JdbcTemplate jdbcTemplate;

    public void checkDataExists(String query, long userId) {
        Boolean isExists = jdbcTemplate.queryForObject(query, Boolean.class, userId);

        if (Boolean.FALSE.equals(isExists)) {
            throw new NotFoundException("Данные с id " + userId + " не найдены");
        }
    }
}
