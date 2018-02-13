package com.demo.hibernate.service;

import com.demo.hibernate.beans.Meeting;
import com.demo.hibernate.util.Page;

public interface MeetingService {

    Page list(int pageSize, int pageNo);

    Meeting select(Integer id);

    Integer insert(Meeting record);

    boolean update(Meeting record);

    boolean delete(Integer id);
}
