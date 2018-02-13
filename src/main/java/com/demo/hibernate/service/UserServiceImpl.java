package com.demo.hibernate.service;

import com.demo.hibernate.beans.User;
import com.demo.hibernate.dao.UserDao;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class UserServiceImpl implements UserService {

    @NonNull
    private UserDao userDao;

    @Override
    public boolean login(String username, String password) {

        Optional<User> record = userDao.select(username);

        return record.isPresent() && record.get().getPassword().equals(password);
    }

    @Override
    public boolean isExist(String username) {
        Optional<User> record = userDao.select(username);
        return record.isPresent();
    }

    @Override
    public Integer register(User record) {
        Optional<User> user = userDao.select(record.getUsername());

        if (user.isPresent()) {
            return null;
        } else {
            return userDao.insert(record);
        }
    }
}
