package com.demo.hibernate.service;

import com.demo.hibernate.beans.Notice;
import com.demo.hibernate.util.Page;

public interface NoticeService {

    Page list(int pageSize, int pageNo);

    Notice select(Integer id);

    Integer insert(Notice record);

    boolean update(Notice record);

    boolean delete(Integer id);
}
