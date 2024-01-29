package ru.yandex.practicum.filmorate.dao.feed;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.FeedMapper;
import ru.yandex.practicum.filmorate.model.Event;

import java.time.Instant;
import java.util.Collection;

@Repository
public class FeedStorageDao {
    private final JdbcTemplate jdbcTemplate;

    public FeedStorageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(long userId, long entityId, String eventType, String operation) {
        String query = "INSERT INTO feed (user_id, entity_id, event_type, operation, time_stamp) VALUES (?,?,?,?,?)";

        jdbcTemplate.update(query, userId, entityId, eventType, operation, Instant.now().getEpochSecond() * 1000);
    }

    public Collection<Event> readUserFeed(long userId) {
        String query = "SELECT * FROM feed WHERE user_id = ?";

        return jdbcTemplate.query(query, new FeedMapper(), userId);
    }
}
