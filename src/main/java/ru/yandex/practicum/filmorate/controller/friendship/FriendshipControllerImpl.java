package ru.yandex.practicum.filmorate.controller.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.friendship.FriendshipServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class FriendshipControllerImpl implements FriendshipController {
    FriendshipServiceImpl friendshipServiceImpl;

    @Autowired
    public FriendshipControllerImpl(FriendshipServiceImpl friendshipServiceImpl) {
        this.friendshipServiceImpl = friendshipServiceImpl;
    }

    @PutMapping("/{userFrom}/friends/{userTo}")
    public void create(@PathVariable long userFrom, @PathVariable long userTo) {
        friendshipServiceImpl.create(userFrom, userTo);
    }

    @GetMapping("/{userId}/friends")
    public Collection<User> readAll(@PathVariable long userId) {
        return friendshipServiceImpl.readAll(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public Collection<User> readCommon(@PathVariable long userId, @PathVariable long otherId) {
        return friendshipServiceImpl.readCommon(userId, otherId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void delete(@PathVariable long userId, @PathVariable long friendId) {
        friendshipServiceImpl.delete(userId, friendId);
    }
}
