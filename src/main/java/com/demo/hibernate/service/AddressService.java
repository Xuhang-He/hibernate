package com.demo.hibernate.service;

import com.demo.hibernate.beans.Address;
import com.demo.hibernate.util.Page;

import javax.servlet.http.HttpServletRequest;

public interface AddressService {

    Page list(String username, int pageSize, int pageNo);

    Address select(Integer id);

    Integer insert(Address record);

    boolean update(Address record);

    boolean delete(Integer id);
}
