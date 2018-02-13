package com.demo.servlet;

import com.demo.hibernate.beans.Address;
import com.demo.hibernate.dao.AddressDao;
import com.demo.hibernate.dao.AddressDaoImpl;
import com.demo.hibernate.service.AddressService;
import com.demo.hibernate.service.AddressServiceImpl;
import com.demo.hibernate.util.Page;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddressServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String method = request.getParameter("method");// 操作方法
        String topage = "/address.jsp";// 跳转页地址

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
            //AddressBean addressBean = new AddressBean();
            AddressDao addressDao = new AddressDaoImpl();
            AddressService addressService = new AddressServiceImpl(addressDao);
            if (method.equals("list")) {// 列表操作
                // 查询数据
                list(request, addressService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/address.jsp";// 跳到列表页
            } else if (method.equals("delete")) {// 删除操作
                // 执行删除
                addressService.delete(Integer.parseInt(request.getParameter("id")));
                // 查询数据
                list(request, addressService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/address.jsp";// 跳到列表页
            } else if (method.equals("add")) {// 新增操作
                topage = "/address_add.jsp";// 跳到新增页
            } else if (method.equals("insert")) {// 插入操作
                // 执行插入
                Address record = new Address();
                String name = request.getParameter("name");
                String sex = request.getParameter("sex");
                String mobile = request.getParameter("mobile");
                String email = request.getParameter("email");
                String qq = request.getParameter("qq");
                String company = request.getParameter("company");
                String address = request.getParameter("address");
                String postcode = request.getParameter("postcode");

                record.setAddress(address);
                record.setCompany(company);
                record.setEmail(email);
                record.setMobile(mobile);
                record.setQq(qq);
                record.setSex(sex);
                record.setPostcode(postcode);
                record.setName(name);
                addressService.insert(record);
                // 查询数据
                list(request, addressService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/address.jsp";// 跳到列表页
            } else if (method.equals("edit")) {// 修改操作
                // 执行查询
                Address address = addressService.select(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("id", address.getId());
                request.setAttribute("username", address.getUsername());
                request.setAttribute("name", address.getName());
                request.setAttribute("sex", address.getSex());
                request.setAttribute("mobile", address.getMobile());
                request.setAttribute("email", address.getEmail());
                request.setAttribute("qq", address.getQq());
                request.setAttribute("company", address.getCompany());
                request.setAttribute("address", address.getAddress());
                request.setAttribute("postcode", address.getPostcode());
                //addressBean.select(request, username);
                topage = "/address_edit.jsp";// 跳到修改页
            } else if (method.equals("update")) {// 更新操作
                // 更新数据
                Address record = new Address();
                String name = request.getParameter("name");
                String sex = request.getParameter("sex");
                String mobile = request.getParameter("mobile");
                String email = request.getParameter("email");
                String qq = request.getParameter("qq");
                String company = request.getParameter("company");
                String address = request.getParameter("address");
                String postcode = request.getParameter("postcode");

                record.setAddress(address);
                record.setCompany(company);
                record.setEmail(email);
                record.setMobile(mobile);
                record.setQq(qq);
                record.setSex(sex);
                record.setPostcode(postcode);
                record.setName(name);
                addressService.update(record);
                //addressBean.update(request, username);
                // 查询数据
                list(request, addressService, username, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
                topage = "/address.jsp";// 跳到列表页
            }
        }

        // 转发
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
                topage);
        rd.forward(request, response);
    }

    private void list(HttpServletRequest request, AddressService addressService, String username, int pageSize, int pageNo) {

        Page page = addressService.list(username, pageSize, pageNo);
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
