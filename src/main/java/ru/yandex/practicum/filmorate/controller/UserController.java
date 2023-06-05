package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.DataService;

@RestController
@RequestMapping("/users")
public class UserController extends DataController<User> {
    @Autowired
    public UserController(@Qualifier("userServiceImpl") DataService<User> dataService) {
        super(dataService);
    }
}
