package com.demo.servlet;

import com.demo.hibernate.beans.Schedule;
import com.demo.hibernate.dao.ScheduleDao;
import com.demo.hibernate.dao.ScheduleDaoImpl;
import com.demo.hibernate.service.ScheduleService;
import com.demo.hibernate.service.ScheduleServiceImpl;
import com.demo.hibernate.util.Page;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ScheduleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String method = request.getParameter("method");// 操作方法
        String topage = "/schedule.jsp";// 跳转页地址
        // 未登录时跳转到登录页面
        if (session.getAttribute("username") == null) {
            topage = "/login.jsp";
        } else {
            String username = (String) session.getAttribute("username");// 当前登录用户名

            // 取得分页参数
            String pageSize = request.getParameter("pageSize");// 每页显示行数
            String pageNo = request.getParameter("pageNo");// 当前显示页次
            if (pageSize == null) {// 为空时设置默认页大小为25
                pageSize = "25";
            }
            if (pageNo == null) {// 为空时设置默认为第1页
                pageNo = "1";
            }
            // 保存分页参数，传递给下一个页面
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("pageNo", pageNo);

            // 根据method参数执行各种操作
            ScheduleDao scheduleDao = new ScheduleDaoImpl();
            ScheduleService scheduleService = new ScheduleServiceImpl(scheduleDao);

            if (method.equals("list")) {// 列表操作
                // 查询数据
                list(request, scheduleService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/schedule.jsp";// 跳到列表页
            } else if (method.equals("delete")) {// 删除操作
                // 执行删除
                scheduleService.delete(Integer.parseInt(request.getParameter("id")));
                // 查询数据
                list(request, scheduleService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/schedule.jsp";// 跳到列表页
            } else if (method.equals("add")) {// 新增操作
                topage = "/schedule_add.jsp";// 跳到新增页
            } else if (method.equals("insert")) {// 插入操作
                // 执行插入
                Schedule record = new Schedule();
                record.setUsername(username);
                record.setYear(Integer.parseInt(request.getParameter("year")));
                record.setMonth(Integer.parseInt(request.getParameter("month")));
                record.setDay(Integer.parseInt(request.getParameter("day")));
                record.setPlan(request.getParameter("plan"));
                scheduleService.insert(record);
                // 查询数据
                list(request, scheduleService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/schedule.jsp";// 跳到列表页
            } else if (method.equals("edit")) {// 修改操作
                // 执行查询
                Schedule schedule = scheduleService.select(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("id", schedule.getId());
                request.setAttribute("username", schedule.getUsername());
                request.setAttribute("year", schedule.getYear());
                request.setAttribute("month", schedule.getMonth());
                request.setAttribute("day", schedule.getDay());
                request.setAttribute("plan", schedule.getPlan());
                topage = "/schedule_edit.jsp";// 跳到修改页
            } else if (method.equals("update")) {// 更新操作
                // 更新数据
                Schedule record = scheduleService.select(Integer.parseInt(request.getParameter("id")));
                record.setYear(Integer.parseInt(request.getParameter("year")));
                record.setMonth(Integer.parseInt(request.getParameter("month")));
                record.setDay(Integer.parseInt(request.getParameter("day")));
                record.setPlan(request.getParameter("plan"));
                scheduleService.update(record);
                // 查询数据
                list(request, scheduleService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/schedule.jsp";// 跳到列表页
            }
        }

        // 转发
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
                topage);
        rd.forward(request, response);
    }

    private void list(HttpServletRequest request, ScheduleService scheduleService, String username, int pageSize, int pageNo) {
        Page page = scheduleService.list(username, pageSize, pageNo);
        request.setAttribute("rowCount", page.getRowCount());
        request.setAttribute("pageCount", page.getPageCount());
        request.setAttribute("pageFirstNo", page.getFirstPageNo());
        request.setAttribute("pageLastNo", page.getLastPageNo());
        request.setAttribute("pagePreNo", page.getPrePageNo());
        request.setAttribute("pageNextNo", page.getNextPageNo());
        request.setAttribute("list", page.getResultList());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
