package com.demo.hibernate.service;

import com.demo.hibernate.beans.Sms;
import com.demo.hibernate.util.Page;

public interface SmsService {

    Page list(String username, int pageSize, int pageNo);

    Sms select(Integer id);

    boolean read(Integer id);

    Integer insert(Sms record);

    boolean update(Sms record);

    boolean delete(Integer id);
}
