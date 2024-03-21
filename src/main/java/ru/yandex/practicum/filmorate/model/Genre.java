package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre extends StorageData {
    @NotBlank(message = "Название жанра не должно быть пустым")
    private String name;

    public Genre(long id, String name) {
        super(id);
        this.name = name;
    }
}