package ru.yandex.practicum.filmorate.controller.genre;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.DataControllerImpl;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

@RestController
@RequestMapping("/genres")
public class GenreControllerImpl extends DataControllerImpl<Genre> implements GenreController {

    public GenreControllerImpl(@Qualifier("genreServiceImpl") DataServiceImpl<Genre> dataServiceImpl) {
        super(dataServiceImpl);
    }
}
