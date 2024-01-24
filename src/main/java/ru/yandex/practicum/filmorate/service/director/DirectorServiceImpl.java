package ru.yandex.practicum.filmorate.service.director;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

@Service
public class DirectorServiceImpl extends DataServiceImpl<Director> implements DirectorService {
    @Autowired
    public DirectorServiceImpl(DataStorageDao<Director> dataStorageDao) {
        super(dataStorageDao);
    }
}