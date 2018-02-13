package com.demo.hibernate.dao;

import com.demo.hibernate.beans.Address;
import com.demo.hibernate.util.Page;

import java.util.Optional;

public interface AddressDao {

    Page list(String username, int pageSize, int pageNo);

    Optional<Address> select(Integer id);

    Integer insert(Address record);

    boolean update(Address record);

    boolean delete(Address record);
}
