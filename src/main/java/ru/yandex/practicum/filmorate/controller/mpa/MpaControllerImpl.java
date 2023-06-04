package ru.yandex.practicum.filmorate.controller.mpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controller.DataControllerImpl;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

@RestController
@RequestMapping("/mpa")
public class MpaControllerImpl extends DataControllerImpl<Mpa> implements MpaController {
    public MpaControllerImpl(@Qualifier("mpaServiceImpl") DataServiceImpl<Mpa> dataServiceImpl) {
        super(dataServiceImpl);
    }
}
