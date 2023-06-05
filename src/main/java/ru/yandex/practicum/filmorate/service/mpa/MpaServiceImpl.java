package ru.yandex.practicum.filmorate.service.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

@Service
public class MpaServiceImpl extends DataServiceImpl<Mpa> implements MpaService {
    @Autowired
    public MpaServiceImpl(DataStorageDao<Mpa> dataStorageDao) {
        super(dataStorageDao);
    }
}
