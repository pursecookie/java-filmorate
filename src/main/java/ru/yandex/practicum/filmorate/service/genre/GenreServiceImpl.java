package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

@Service
public class GenreServiceImpl extends DataServiceImpl<Genre> implements GenreService {
    @Autowired
    public GenreServiceImpl(DataStorageDaoImpl<Genre> dataStorageDaoImpl) {
        super(dataStorageDaoImpl);
    }
}
