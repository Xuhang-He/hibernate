package com.demo.servlet;

import com.demo.hibernate.beans.Worklog;
import com.demo.hibernate.dao.WorklogDao;
import com.demo.hibernate.dao.WorklogDaoImpl;
import com.demo.hibernate.service.WorklogService;
import com.demo.hibernate.service.WorklogServiceImpl;
import com.demo.hibernate.util.Page;
import com.demo.javabean.WorklogBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorklogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String method = request.getParameter("method");// 操作方法
        String topage = "/worklog.jsp";// 跳转页地址
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
            //WorklogBean worklogBean = new WorklogBean();
            WorklogDao worklogDao = new WorklogDaoImpl();
            WorklogService worklogService = new WorklogServiceImpl(worklogDao);
            if (method.equals("list")) {// 列表操作
                // 查询数据
                list(request, worklogService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/worklog.jsp";// 跳到列表页
            } else if (method.equals("delete")) {// 删除操作
                // 执行删除
                worklogService.delete(Integer.parseInt(request.getParameter("id")));
                // 查询数据
                list(request, worklogService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/worklog.jsp";// 跳到列表页
            } else if (method.equals("add")) {// 新增操作
                topage = "/worklog_add.jsp";// 跳到新增页
            } else if (method.equals("insert")) {// 插入操作
                // 执行插入
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String logtime = format.format(new Date());
                Worklog record = new Worklog();
                record.setUsername(username);
                record.setLogtime(logtime);
                record.setYear(Integer.parseInt(request.getParameter("year")));
                record.setMonth(Integer.parseInt(request.getParameter("month")));
                record.setDay(Integer.parseInt(request.getParameter("day")));
                record.setDescription(request.getParameter("description"));
                record.setTitle(request.getParameter("title"));
                worklogService.insert(record);
                // 查询数据
                list(request, worklogService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/worklog.jsp";// 跳到列表页
            } else if (method.equals("edit")) {// 修改操作
                // 执行查询
                Worklog worklog = worklogService.select(Integer.parseInt(request.getParameter("id")));

                request.setAttribute("id", worklog.getId());
                request.setAttribute("username", worklog.getUsername());
                request.setAttribute("year", worklog.getYear());
                request.setAttribute("month", worklog.getMonth());
                request.setAttribute("day", worklog.getDay());
                request.setAttribute("title", worklog.getTitle());
                request.setAttribute("description", worklog.getDescription());
                request.setAttribute("logtime", worklog.getLogtime());

                topage = "/worklog_edit.jsp";// 跳到修改页
            } else if (method.equals("update")) {// 更新操作
                // 更新数据
                Worklog worklog = worklogService.select(Integer.parseInt(request.getParameter("id")));
                worklog.setYear(Integer.parseInt(request.getParameter("year")));
                worklog.setMonth(Integer.parseInt(request.getParameter("month")));
                worklog.setDay(Integer.parseInt(request.getParameter("day")));
                worklog.setTitle(request.getParameter("title"));
                worklog.setDescription(request.getParameter("description"));
                worklogService.update(worklog);

                // 查询数据
                list(request, worklogService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/worklog.jsp";// 跳到列表页
            }
        }

        // 转发
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
                topage);
        rd.forward(request, response);
    }

    private void list(HttpServletRequest request, WorklogService worklogService, String username, int pageSize, int pageNo) {
        Page page = worklogService.list(username, pageSize, pageNo);
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
