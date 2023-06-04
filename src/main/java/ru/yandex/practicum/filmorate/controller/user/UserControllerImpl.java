package ru.yandex.practicum.filmorate.controller.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.controller.DataControllerImpl;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

@RestController
@RequestMapping("/users")
public class UserControllerImpl extends DataControllerImpl<User> implements UserController {
    public UserControllerImpl(@Qualifier("userServiceImpl") DataServiceImpl<User> dataServiceImpl) {
        super(dataServiceImpl);
    }
}
