package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.DataService;

@RestController
@RequestMapping("/films")
public class FilmController extends DataController<Film> {
    @Autowired
    public FilmController(@Qualifier("filmServiceImpl") DataService<Film> dataService) {
        super(dataService);
    }
}
