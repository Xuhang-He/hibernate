package com.demo.servlet;

import com.demo.hibernate.beans.Notice;
import com.demo.hibernate.dao.NoticeDao;
import com.demo.hibernate.dao.NoticeDaoImpl;
import com.demo.hibernate.service.NoticeService;
import com.demo.hibernate.service.NoticeServiceImpl;
import com.demo.hibernate.util.Page;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String method = request.getParameter("method");// 操作方法
        String topage = "/notice.jsp";// 跳转页地址
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
            //NoticeBean noticeBean = new NoticeBean();
            NoticeDao noticeDao = new NoticeDaoImpl();
            NoticeService noticeService = new NoticeServiceImpl(noticeDao);
            if (method.equals("list")) {// 列表操作
                // 查询数据
                list(request, noticeService, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/notice.jsp";// 跳到列表页
            } else if (method.equals("delete")) {// 删除操作
                // 执行删除
                //noticeBean.delete(request, username);
                noticeService.delete(Integer.parseInt(request.getParameter("id")));
                // 查询数据
                list(request, noticeService, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/notice.jsp";// 跳到列表页
            } else if (method.equals("add")) {// 新增操作
                topage = "/notice_add.jsp";// 跳到新增页
            } else if (method.equals("insert")) {// 插入操作
                // 执行插入
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String sendtime = format.format(new Date());
                Notice record = new Notice();
                record.setSender(username);
                record.setSendtime(sendtime);
                record.setContent(request.getParameter("content"));
                record.setTitle(request.getParameter("title"));
                noticeService.insert(record);
                // 查询数据
                list(request, noticeService, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/notice.jsp";// 跳到列表页
            } else if (method.equals("edit")) {// 修改操作
                // 执行查询
                //noticeBean.select(request, username);
                Notice notice = noticeService.select(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("id", notice.getId());
                request.setAttribute("sender", notice.getSender());
                request.setAttribute("title", notice.getTitle());
                request.setAttribute("content", notice.getContent());
                request.setAttribute("sendtime", notice.getSendtime());
                topage = "/notice_edit.jsp";// 跳到修改页
            } else if (method.equals("update")) {// 更新操作
                // 更新数据
                //noticeBean.update(request, username);
                Notice record = new Notice();
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String sendtime = format.format(new Date());
                record.setId(Integer.parseInt(request.getParameter("id")));
                record.setTitle(request.getParameter("id"));
                record.setContent(request.getParameter("content"));
                record.setSender(username);
                record.setSendtime(sendtime);
                noticeService.update(record);
                // 查询数据
                list(request, noticeService, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/notice.jsp";// 跳到列表页
            }
        }

        // 转发
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
                topage);
        rd.forward(request, response);
    }

    private void list(HttpServletRequest request, NoticeService noticeService, int pageSize, int pageNo) {
        Page page = noticeService.list(pageSize, pageNo);
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
