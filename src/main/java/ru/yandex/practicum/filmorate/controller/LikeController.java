package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.like.LikeService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void create(@PathVariable long filmId, @PathVariable long userId) {
        likeService.create(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> readPopular(@RequestParam(required = false) String count) {
        return likeService.readPopular(count);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void delete(@PathVariable long filmId, @PathVariable long userId) {
        likeService.delete(filmId, userId);
    }
}
