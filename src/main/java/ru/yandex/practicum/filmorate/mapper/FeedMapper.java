package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedMapper implements RowMapper<Event> {
    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        Event event = new Event();

        event.setEventId(rs.getLong("event_id"));
        event.setUserId(rs.getLong("user_id"));
        event.setEntityId(rs.getLong("entity_id"));
        event.setEventType(rs.getString("event_type"));
        event.setOperation(rs.getString("operation"));
        event.setTimestamp(rs.getLong("time_stamp"));

        return event;
    }
}
