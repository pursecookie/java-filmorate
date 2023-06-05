package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.DataService;

@RestController
@RequestMapping("/genres")
public class GenreController extends DataController<Genre> {
    @Autowired
    public GenreController(@Qualifier("genreServiceImpl") DataService<Genre> dataService) {
        super(dataService);
    }
}
