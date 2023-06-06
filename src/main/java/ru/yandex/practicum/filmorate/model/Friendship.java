package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friendship {
    private long id;
    private long userFrom;
    private long userTo;
    private boolean isApproved;
}
