package com.demo.hibernate.service;

import com.demo.hibernate.beans.User;
import com.demo.hibernate.dao.UserDao;
import com.demo.hibernate.dao.UserDaoImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserServiceTest{
    private UserService userService;

    @Before
    public void setup() {
        UserDao userDao = new UserDaoImpl();
        userService = new UserServiceImpl(userDao);
    }

    @Test
    public void testLogin() {
        assertThat(userService.login("admin", "admin"), is(true));
    }

    @Test
    public void testIsExist() {
        assertThat(userService.isExist("admin"), is(true));
    }

    @Test
    public void testRegister() {
        String username = UUID.randomUUID().toString();
        User record = User.builder()
                .username(username)
                .password("123")
                .email("123@gmail.com")
                .build();

        userService.register(record);
        assertThat(userService.isExist(username), is(true));
    }
}