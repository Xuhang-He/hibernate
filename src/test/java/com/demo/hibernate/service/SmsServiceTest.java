package com.demo.hibernate.service;

import com.demo.hibernate.beans.Sms;
import com.demo.hibernate.util.Page;
import com.demo.hibernate.dao.SmsDao;
import com.demo.hibernate.dao.SmsDaoImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SmsServiceTest {

    private SmsService smsService;

    @Before
    public void setUp() {
        SmsDao smsDao = new SmsDaoImpl();
        smsService = new SmsServiceImpl(smsDao);
    }

    @Test
    public void list() {
        Page page = smsService.list("admin", 25, 1);
        assertTrue(page.getRowCount() > 0);
        for (Object o : page.getResultList()) {
            Sms address = (Sms) o;
            assertNotNull(address);
        }
    }

    @Test
    public void select() {
        assertNotNull(smsService.select(1));
    }

    @Test
    public void insert() {
        Sms sms = new Sms();
        sms.setUsername("admin");
        sms.setSender("admin");
        sms.setMessage("JavaWeb");
        sms.setSendtime("2008-10-10");
        sms.setIsRead("0");
        assertNotNull(smsService.insert(sms));
    }

    @Test
    public void update() {
        Sms sms = new Sms();
        sms.setId(1);
        sms.setUsername("admin");
        sms.setSender("admin");
        sms.setMessage("JavaWeb");
        sms.setSendtime("2008-10-10");
        sms.setIsRead("1");
        smsService.update(sms);

        Sms sms2 = smsService.select(1);
        assertTrue(sms2.getIsRead().equals("1"));
    }
}