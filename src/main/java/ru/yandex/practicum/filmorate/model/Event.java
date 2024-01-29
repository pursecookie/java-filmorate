package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Event {
    private long eventId;
    private long userId;
    private long entityId;
    private String eventType;
    private String operation;
    private long timestamp;
}