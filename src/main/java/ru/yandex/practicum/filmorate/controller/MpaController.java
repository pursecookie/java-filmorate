package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.DataService;

@RestController
@RequestMapping("/mpa")
public class MpaController extends DataController<Mpa> {
    @Autowired
    public MpaController(@Qualifier("mpaServiceImpl") DataService<Mpa> dataService) {
        super(dataService);
    }
}
