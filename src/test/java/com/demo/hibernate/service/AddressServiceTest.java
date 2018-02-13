package com.demo.hibernate.service;

import com.demo.hibernate.beans.Address;
import com.demo.hibernate.dao.AddressDao;
import com.demo.hibernate.dao.AddressDaoImpl;
import com.demo.hibernate.util.Page;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AddressServiceTest {

    private AddressService addressService;

    @Before
    public void setUp() {
        AddressDao addressDao = new AddressDaoImpl();
        addressService = new AddressServiceImpl(addressDao);
    }

    @Test
    public void testList() {
        Page page = addressService.list("admin", 25, 1);
        assertTrue(page.getRowCount() > 0);
        for (Object o : page.getResultList()) {
            Address address = (Address) o;
            assertNotNull(address);
        }
    }

    @Test
    public void testSelect() {
        assertNotNull(addressService.select(1));
    }

    @Test
    public void testInsert() {
        Address address = new Address();
        address.setUsername("admin");
        address.setName("andy");
        address.setSex("2");
        address.setMobile("13888886666");
        address.setEmail("andy@163.com");
        address.setQq("12345678");
        address.setCompany("Intel");
        address.setAddress("北京");
        address.setPostcode("200089");
        int id = addressService.insert(address);
        assertNotNull(id);
    }

    @Test
    public void testUpdate() {
        Address address = new Address();
        address.setId(1);
        address.setUsername("admin");
        address.setName("andy");
        address.setSex("2");
        address.setMobile("13888886666");
        address.setEmail("andy@163.com");
        address.setQq("12345678");
        address.setCompany("Microsoft");
        address.setAddress("北京");
        address.setPostcode("200089");
        boolean result = addressService.update(address);

        Address address2 = addressService.select(1);
        assertTrue(address2.getCompany().equals("Microsoft"));
        assertTrue(result);
    }
}