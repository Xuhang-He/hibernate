package com.demo.hibernate.service;

import com.demo.hibernate.beans.Worklog;
import com.demo.hibernate.util.Page;
import com.demo.hibernate.dao.WorklogDao;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class WorklogServiceImpl implements WorklogService {

    @NonNull
    private WorklogDao worklogDao;

    @Override
    public Page list(String username, int pageSize, int pageNo) {
        return worklogDao.list(username, pageSize, pageNo);
    }

    @Override
    public Worklog select(Integer id) {
        return worklogDao.select(id).orElse(null);
    }

    @Override
    public Integer insert(Worklog record) {
        return worklogDao.insert(record);
    }

    @Override
    public boolean update(Worklog record) {
        return worklogDao.update(record);
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Worklog> record = worklogDao.select(id);
        return record.filter(worklogDao::delete).isPresent();
    }
}
