package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DataService;

@RestController
@RequestMapping("/directors")
public class DirectorController extends DataController<Director> {
    @Autowired
    public DirectorController(@Qualifier("directorServiceImpl") DataService<Director> dataService) {
        super(dataService);
    }
}