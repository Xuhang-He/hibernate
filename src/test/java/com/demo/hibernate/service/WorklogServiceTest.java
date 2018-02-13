package com.demo.hibernate.service;

import com.demo.hibernate.beans.Worklog;
import com.demo.hibernate.util.Page;
import com.demo.hibernate.dao.WorklogDao;
import com.demo.hibernate.dao.WorklogDaoImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorklogServiceTest {

    private WorklogService worklogService;

    @Before
    public void setUp() {
        WorklogDao worklogDao = new WorklogDaoImpl();
        worklogService = new WorklogServiceImpl(worklogDao);
    }

    @Test
    public void list() {
        Page page = worklogService.list("admin", 25, 1);
        assertTrue(page.getRowCount() > 0);
        for (Object o : page.getResultList()) {
            Worklog address = (Worklog) o;
            assertNotNull(address);
        }
    }

    @Test
    public void select() {
        assertNotNull(worklogService.select(1));
    }

    @Test
    public void insert() {
        Worklog worklog = new Worklog();
        worklog.setUsername("admin");
        worklog.setYear(2007);
        worklog.setMonth(8);
        worklog.setDay(30);
        worklog.setTitle("JavaWeb");
        worklog.setDescription("JavaWeb");
        worklog.setLogtime("2008-10-10");
        assertNotNull(worklogService.insert(worklog));
    }

    @Test
    public void update() {
        Worklog worklog = new Worklog();
        worklog.setId(1);
        worklog.setUsername("admin");
        worklog.setYear(2007);
        worklog.setMonth(6);
        worklog.setDay(30);
        worklog.setTitle("JavaWeb");
        worklog.setDescription("JavaWeb");
        worklog.setLogtime("2008-10-10");
        worklogService.update(worklog);

        Worklog worklog2 = worklogService.select(1);
        assertTrue(worklog2.getMonth() ==6);
    }
}