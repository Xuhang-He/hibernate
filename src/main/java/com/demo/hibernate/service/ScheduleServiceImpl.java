package com.demo.hibernate.service;

import com.demo.hibernate.beans.Schedule;
import com.demo.hibernate.util.Page;
import com.demo.hibernate.dao.ScheduleDao;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class ScheduleServiceImpl implements ScheduleService {

    @NonNull
    private ScheduleDao scheduleDao;

    @Override
    public Page list(String username, int pageSize, int pageNo) {
        return scheduleDao.list(username, pageSize, pageNo);
    }

    @Override
    public Schedule select(Integer id) {
        return scheduleDao.select(id).orElse(null);
    }

    @Override
    public Integer insert(Schedule record) {
        return scheduleDao.insert(record);
    }

    @Override
    public boolean update(Schedule record) {
        return scheduleDao.update(record);
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Schedule> record = scheduleDao.select(id);
        return record.filter(scheduleDao::delete).isPresent();
    }
}
