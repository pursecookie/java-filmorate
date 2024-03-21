package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class Review extends StorageData {
    private Long reviewId;
    @NotBlank(message = "Содержание отзыва не должно быть пустым")
    private String content;
    @NotNull(message = "Тип отзыва не может быть пустым")
    private Boolean isPositive;
    @NotNull(message = "Id автора отзыва не может быть пустым")
    private Long userId;
    @NotNull(message = "Id фильма, на который оставлен отзыв, не может быть пустым")
    private Long filmId;
    private Long useful;
}