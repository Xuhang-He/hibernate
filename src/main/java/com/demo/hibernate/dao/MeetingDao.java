package com.demo.hibernate.dao;

import com.demo.hibernate.beans.Meeting;
import com.demo.hibernate.util.Page;

import java.util.Optional;

public interface MeetingDao {

    Page list(int pageSize, int pageNo);

    Optional<Meeting> select(Integer id);

    Integer insert(Meeting record);

    boolean update(Meeting record);

    boolean delete(Meeting record);
}
