package ru.yandex.practicum.filmorate.model;

public class Counter {
    public long counter = 1;

    public long count() {
        return counter++;
    }
}
