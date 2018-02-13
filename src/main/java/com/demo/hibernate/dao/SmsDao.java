package com.demo.hibernate.dao;

import com.demo.hibernate.beans.Sms;
import com.demo.hibernate.util.Page;

import java.util.Optional;

public interface SmsDao {

    Page list(String username, int pageSize, int pageNo);

    Optional<Sms> select(Integer id);

    Integer insert(Sms record);

    boolean update(Sms record);

    boolean delete(Sms record);
}
