package com.demo.hibernate.dao;

import com.demo.hibernate.beans.Notice;
import com.demo.hibernate.util.Page;

import java.util.Optional;

public interface NoticeDao {

    Page list(int pageSize, int pageNo);

    Optional<Notice> select(Integer id);

    Integer insert(Notice record);

    boolean update(Notice record);

    boolean delete(Notice record);
}
