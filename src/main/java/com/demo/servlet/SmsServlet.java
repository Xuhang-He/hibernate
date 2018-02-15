package com.demo.servlet;

import com.demo.hibernate.beans.Sms;
import com.demo.hibernate.dao.SmsDao;
import com.demo.hibernate.dao.SmsDaoImpl;
import com.demo.hibernate.service.SmsService;
import com.demo.hibernate.service.SmsServiceImpl;
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

public class SmsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String method = request.getParameter("method");// 操作方法
        String topage = "/sms.jsp";// 跳转页地址
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
            //SmsBean smsBean = new SmsBean();
            SmsDao smsDao = new SmsDaoImpl();
            SmsService smsService = new SmsServiceImpl(smsDao);
            if (method.equals("list")) {// 列表操作
                // 查询数据
                list(request, smsService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/sms.jsp";// 跳到列表页
            } else if (method.equals("delete")) {// 删除操作
                // 执行删除
                smsService.delete(Integer.parseInt(request.getParameter("id")));
                // 查询数据
                list(request, smsService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/sms.jsp";// 跳到列表页
            } else if (method.equals("add")) {// 新增操作
                topage = "/sms_add.jsp";// 跳到新增页
            } else if (method.equals("insert")) {// 插入操作
                // 执行插入
                Sms record = new Sms();
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String sendtime = format.format(new Date());
                record.setUsername(request.getParameter("username"));
                record.setMessage(request.getParameter("message"));
                record.setSender(request.getParameter("username"));
                record.setIsRead("0");
                record.setSendtime(sendtime);
                smsService.insert(record);
                // 查询数据
                list(request, smsService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/sms.jsp";// 跳到列表页
            } else if (method.equals("read")) {// 更新操作
                // 更新数据
                smsService.read(Integer.parseInt(request.getParameter("id")));
                // 查询数据
                list(request, smsService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/sms.jsp";// 跳到列表页
            }
        }

        // 转发
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
                topage);
        rd.forward(request, response);
    }

    private void list(HttpServletRequest request, SmsService smsService, String username, int pageSize, int pageNo) {
        Page page = smsService.list(username, pageSize, pageNo);
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
