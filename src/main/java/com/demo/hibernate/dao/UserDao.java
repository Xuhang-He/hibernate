package com.demo.hibernate.dao;

import com.demo.hibernate.beans.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> select(String username);

    Integer insert(User userRecord);
}
