package com.demo.hibernate.dao;

import com.demo.hibernate.beans.Worklog;
import com.demo.hibernate.util.Page;

import java.util.Optional;

public interface WorklogDao {

    Page list(String username, int pageSize, int pageNo);

    Optional<Worklog> select(Integer id);

    Integer insert(Worklog record);

    boolean update(Worklog record);

    boolean delete(Worklog record);
}
