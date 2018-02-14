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
import java.io.IOException;
import java.io.PrintWriter;

public class AjaxServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
        String username = request.getParameter("username");
        
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(userDao);
        boolean isExist = userService.isExist(username);

        PrintWriter out =response.getWriter();

        if(isExist) {
            out.println("<content>the username already exists!</content>");

        }else{
            out.println("<content>ok</content>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
