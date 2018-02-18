package com.demo.servlet;

import com.demo.hibernate.beans.User;
import com.demo.hibernate.dao.UserDao;
import com.demo.hibernate.dao.UserDaoImpl;
import com.demo.hibernate.service.UserService;
import com.demo.hibernate.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String password1 = req.getParameter("password1");
        String email = req.getParameter("email");

        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(userDao);
        boolean isExist = userService.isExist(username);

        if (!isExist) {
            User record = new User();
            record.setUsername(username);
            record.setEmail(email);
            record.setPassword(password1);
            userService.register(record);
            session.setAttribute("username", username);
            resp.sendRedirect("welcome.jsp");
        } else {
            resp.sendRedirect("register.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
