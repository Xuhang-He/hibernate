package com.demo.hibernate.service;

import com.demo.hibernate.beans.Worklog;
import com.demo.hibernate.util.Page;

public interface WorklogService {

    Page list(String username, int pageSize, int pageNo);

    Worklog select(Integer id);

    Integer insert(Worklog record);

    boolean update(Worklog record);

    boolean delete(Integer id);
}
