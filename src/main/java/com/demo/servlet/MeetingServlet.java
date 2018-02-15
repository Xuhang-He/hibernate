package com.demo.servlet;

import com.demo.hibernate.beans.Meeting;
import com.demo.hibernate.dao.MeetingDao;
import com.demo.hibernate.dao.MeetingDaoImpl;
import com.demo.hibernate.service.MeetingService;
import com.demo.hibernate.service.MeetingServiceImpl;
import com.demo.hibernate.util.Page;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MeetingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String method = request.getParameter("method");// 操作方法
        String topage = "/meeting.jsp";// 跳转页地址
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
            //MeetingBean meetingBean = new MeetingBean();
            MeetingDao meetingDao = new MeetingDaoImpl();
            MeetingService meetingService = new MeetingServiceImpl(meetingDao);
            if (method.equals("list")) {// 列表操作
                // 查询数据
                list(request, meetingService, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/meeting.jsp";// 跳到列表页
            } else if (method.equals("delete")) {// 删除操作
                // 执行删除
                //meetingBean.delete(request, username);
                meetingService.delete(Integer.parseInt(request.getParameter("id")));
                // 查询数据
                list(request, meetingService, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/meeting.jsp";// 跳到列表页
            } else if (method.equals("add")) {// 新增操作
                topage = "/meeting_add.jsp";// 跳到新增页
            } else if (method.equals("insert")) {// 插入操作
                // 执行插入
                Meeting record = new Meeting();
                record.setSender(username);
                record.setAddress(request.getParameter("address"));
                record.setEndtime(request.getParameter("endtime"));
                record.setContent(request.getParameter("content"));
                record.setTitle(request.getParameter("title"));
                record.setStarttime(request.getParameter("starttime"));

                meetingService.insert(record);
                // 查询数据
                list(request, meetingService, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/meeting.jsp";// 跳到列表页
            } else if (method.equals("edit")) {// 修改操作
                // 执行查询
                //meetingBean.select(request, username);
                Meeting meeting = meetingService.select(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("id", meeting.getId());
                request.setAttribute("sender", meeting.getSender());
                request.setAttribute("starttime", meeting.getStarttime());
                request.setAttribute("endtime", meeting.getEndtime());
                request.setAttribute("address", meeting.getAddress());
                request.setAttribute("title", meeting.getTitle());
                request.setAttribute("content", meeting.getContent());

                topage = "/meeting_edit.jsp";// 跳到修改页
            } else if (method.equals("update")) {// 更新操作
                // 更新数据
                Meeting record = meetingService.select(Integer.parseInt(request.getParameter("id")));
                record.setStarttime(request.getParameter("starttime"));
                record.setTitle(request.getParameter("title"));
                record.setEndtime(request.getParameter("endtime"));
                record.setContent(request.getParameter("content"));
                record.setAddress(request.getParameter("address"));
                record.setSender(username);
                meetingService.update(record);
                // 查询数据
                list(request, meetingService, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/meeting.jsp";// 跳到列表页
            }
        }

        // 转发
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
                topage);
        rd.forward(request, response);
    }

    private void list(HttpServletRequest request, MeetingService meetingService, int pageSize, int pageNo) {
        Page page = meetingService.list(pageSize, pageNo);
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
