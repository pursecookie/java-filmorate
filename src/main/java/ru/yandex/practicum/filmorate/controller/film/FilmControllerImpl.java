package ru.yandex.practicum.filmorate.controller.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.DataControllerImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmControllerImpl extends DataControllerImpl<Film> implements FilmController {

    @Autowired
    public FilmControllerImpl(@Qualifier("filmServiceImpl") DataServiceImpl<Film> dataServiceImpl) {
        super(dataServiceImpl);
    }
}
