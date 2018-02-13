package com.demo.hibernate.service;

import com.demo.hibernate.beans.Schedule;
import com.demo.hibernate.util.Page;

public interface ScheduleService {

    Page list(String username, int pageSize, int pageNo);

    Schedule select(Integer id);

    Integer insert(Schedule record);

    boolean update(Schedule record);

    boolean delete(Integer id);
}
