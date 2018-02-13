package com.demo.hibernate.service;

import com.demo.hibernate.beans.Notice;
import com.demo.hibernate.util.Page;
import com.demo.hibernate.dao.NoticeDao;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class NoticeServiceImpl implements NoticeService {

    @NonNull
    private NoticeDao noticeDao;

    @Override
    public Page list(int pageSize, int pageNo) {
        return noticeDao.list(pageSize, pageNo);
    }

    @Override
    public Notice select(Integer id) {
        return noticeDao.select(id).orElse(null);
    }

    @Override
    public Integer insert(Notice record) {
        return noticeDao.insert(record);
    }

    @Override
    public boolean update(Notice record) {
        return noticeDao.update(record);
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Notice> record = noticeDao.select(id);
        return record.filter(noticeDao::delete).isPresent();
    }
}
