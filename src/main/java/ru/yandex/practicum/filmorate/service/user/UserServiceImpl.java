package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

@Service
public class UserServiceImpl extends DataServiceImpl<User> implements UserService {
    @Autowired
    protected UserServiceImpl(DataStorageDaoImpl<User> dataStorageDaoImpl) {
        super(dataStorageDaoImpl);
    }
}
