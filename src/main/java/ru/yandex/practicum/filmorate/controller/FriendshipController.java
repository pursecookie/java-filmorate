package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.friendship.FriendshipService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class FriendshipController {
    FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PutMapping("/{userFrom}/friends/{userTo}")
    public void create(@PathVariable long userFrom, @PathVariable long userTo) {
        friendshipService.create(userFrom, userTo);
    }

    @GetMapping("/{userId}/friends")
    public Collection<User> readAll(@PathVariable long userId) {
        return friendshipService.readAll(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public Collection<User> readCommon(@PathVariable long userId, @PathVariable long otherId) {
        return friendshipService.readCommon(userId, otherId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void delete(@PathVariable long userId, @PathVariable long friendId) {
        friendshipService.delete(userId, friendId);
    }
}
