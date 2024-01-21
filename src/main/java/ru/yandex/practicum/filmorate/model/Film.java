package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.annotation.MinReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class Film extends StorageData {
    @NotBlank(message = "Название фильма не должно быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальное количество символов для описания - 200")
    private String description;
    @MinReleaseDate
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private long duration;
    private Mpa mpa;
    private Set<Genre> genres = new HashSet<>();
}
