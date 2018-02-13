package com.demo.hibernate.service;

import com.demo.hibernate.beans.Address;
import com.demo.hibernate.dao.AddressDao;
import com.demo.hibernate.util.Page;
import lombok.NonNull;
import lombok.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Value
public class AddressServiceImpl implements AddressService {

    @NonNull
    private AddressDao addressDao;

    @Override
    public Page list(String username, int pageSize, int pageNo) {
        return addressDao.list(username, pageSize, pageNo);
    }

    @Override
    public Address select(Integer id) {
        return addressDao.select(id).orElse(null);
    }

    @Override
    public Integer insert(Address record) {
        return addressDao.insert(record);
    }

    @Override
    public boolean update(Address record) {
        return addressDao.update(record);
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Address> record = addressDao.select(id);
        return record.filter(addressDao::delete).isPresent();
    }
}
