package com.demo.hibernate.dao;

import com.demo.hibernate.beans.Schedule;
import com.demo.hibernate.util.Page;

import java.util.Optional;

public interface ScheduleDao {

    Page list(String username, int pageSize, int pageNo);

    Optional<Schedule> select(Integer id);

    Integer insert(Schedule record);

    boolean update(Schedule record);

    boolean delete(Schedule record);
}
