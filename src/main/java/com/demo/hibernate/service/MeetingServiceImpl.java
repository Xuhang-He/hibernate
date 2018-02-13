package com.demo.hibernate.service;

import com.demo.hibernate.beans.Meeting;
import com.demo.hibernate.util.Page;
import com.demo.hibernate.dao.MeetingDao;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class MeetingServiceImpl implements MeetingService {

    @NonNull
    private MeetingDao meetingDao;
    @Override
    public Page list(int pageSize, int pageNo) {
        return meetingDao.list(pageSize, pageNo);
    }

    @Override
    public Meeting select(Integer id) {
        return meetingDao.select(id).orElse(null);
    }

    @Override
    public Integer insert(Meeting record) {
        return meetingDao.insert(record);
    }

    @Override
    public boolean update(Meeting record) {
        return meetingDao.update(record);
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Meeting> record = meetingDao.select(id);
        return record.filter(meetingDao::delete).isPresent();
    }
}
