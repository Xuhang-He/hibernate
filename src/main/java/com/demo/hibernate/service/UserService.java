package com.demo.hibernate.service;

import com.demo.hibernate.beans.User;

public interface UserService {

    boolean login(String username, String password);

    boolean isExist(String username);

    Integer register(User record);
}
