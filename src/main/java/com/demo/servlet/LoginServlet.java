package com.demo.servlet;

import com.demo.hibernate.dao.UserDao;
import com.demo.hibernate.dao.UserDaoImpl;
import com.demo.hibernate.service.UserService;
import com.demo.hibernate.service.UserServiceImpl;
import com.demo.javabean.UserBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(userDao);
        boolean isValid = userService.login(username, password);

        if (isValid) {
           session.setAttribute("username", username);
           resp.sendRedirect("welcome.jsp");
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
