package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.DataService;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController extends DataController<User> {
    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("userServiceImpl") DataService<User> dataService, UserService userService) {
        super(dataService);
        this.userService = userService;
    }

    @PutMapping("/{userFrom}/friends/{userTo}")
    public void create(@PathVariable long userFrom, @PathVariable long userTo) {
        userService.createFriendship(userFrom, userTo);
    }

    @GetMapping("/{userId}/friends")
    public Collection<User> readAll(@PathVariable long userId) {
        return userService.readAllFriendships(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public Collection<User> readCommon(@PathVariable long userId, @PathVariable long otherId) {
        return userService.readCommonFriends(userId, otherId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void delete(@PathVariable long userId, @PathVariable long friendId) {
        userService.deleteFriendship(userId, friendId);
    }

    @GetMapping("/{userId}/feed")
    public Collection<Event> readUserFeed(@PathVariable long userId) {
        return userService.readUserFeed(userId);
    }
}
