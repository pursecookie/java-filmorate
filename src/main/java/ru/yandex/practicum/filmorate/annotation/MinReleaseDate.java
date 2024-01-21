package ru.yandex.practicum.filmorate.annotation;

import ru.yandex.practicum.filmorate.validator.MinReleaseDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = MinReleaseDateValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface MinReleaseDate {
    String message() default "Дата релиза не может быть раньше 28.12.1895 г.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
