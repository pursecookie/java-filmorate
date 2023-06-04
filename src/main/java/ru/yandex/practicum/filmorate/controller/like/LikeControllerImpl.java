package ru.yandex.practicum.filmorate.controller.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.like.LikeServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class LikeControllerImpl implements LikeController {
    LikeServiceImpl likeServiceImpl;

    @Autowired
    public LikeControllerImpl(LikeServiceImpl likeServiceImpl) {
        this.likeServiceImpl = likeServiceImpl;
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void create(@PathVariable long filmId, @PathVariable long userId) {
        likeServiceImpl.create(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> readPopular(@RequestParam(required = false) String count) {
        return likeServiceImpl.readPopular(count);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void delete(@PathVariable long filmId, @PathVariable long userId) {
        likeServiceImpl.delete(filmId, userId);
    }
}
