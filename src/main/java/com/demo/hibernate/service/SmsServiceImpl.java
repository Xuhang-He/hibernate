package com.demo.hibernate.service;

import com.demo.hibernate.beans.Sms;
import com.demo.hibernate.util.Page;
import com.demo.hibernate.dao.SmsDao;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class SmsServiceImpl implements SmsService {

    @NonNull
    private SmsDao smsDao;

    @Override
    public Page list(String username, int pageSize, int pageNo) {
        return smsDao.list(username, pageSize, pageNo);
    }

    @Override
    public Sms select(Integer id) {
        return smsDao.select(id).orElse(null);
    }

    @Override
    public boolean read(Integer id) {
        Optional<Sms> record = smsDao.select(id);
        if (record.isPresent()) {
            record.get().setIsRead("1");
            return smsDao.update(record.get());
        }
        return false;
    }

    @Override
    public Integer insert(Sms record) {
        return smsDao.insert(record);
    }

    @Override
    public boolean update(Sms record) {
        return smsDao.update(record);
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Sms> record = smsDao.select(id);
        return record.filter(smsDao::delete).isPresent();
    }
}
