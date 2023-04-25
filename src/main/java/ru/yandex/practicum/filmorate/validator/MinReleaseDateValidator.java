package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.annotation.MinReleaseDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MinReleaseDateValidator implements ConstraintValidator<MinReleaseDate, LocalDate> {
    private static final LocalDate EARLIEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext context) {
        return releaseDate.isAfter(EARLIEST_RELEASE_DATE);
    }
}
