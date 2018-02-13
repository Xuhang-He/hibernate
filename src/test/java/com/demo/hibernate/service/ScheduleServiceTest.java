package com.demo.hibernate.service;

import com.demo.hibernate.beans.Schedule;
import com.demo.hibernate.util.Page;
import com.demo.hibernate.dao.ScheduleDao;
import com.demo.hibernate.dao.ScheduleDaoImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ScheduleServiceTest {

    private ScheduleService scheduleService;

    @Before
    public void setUp() {
        ScheduleDao scheduleDao = new ScheduleDaoImpl();
        scheduleService = new ScheduleServiceImpl(scheduleDao);
    }

    @Test
    public void list() {
        Page page = scheduleService.list("admin", 25, 1);
        assertTrue(page.getRowCount() > 0);
        for (Object o : page.getResultList()) {
            Schedule address = (Schedule) o;
            assertNotNull(address);
        }
    }

    @Test
    public void select() {
        assertNotNull(scheduleService.select(1));
    }

    @Test
    public void insert() {
        Schedule schedule = new Schedule();
        schedule.setUsername("admin");
        schedule.setYear(2007);
        schedule.setMonth(8);
        schedule.setDay(30);
        schedule.setPlan("JavaWeb");
        assertNotNull(scheduleService.insert(schedule));
    }

    @Test
    public void update() {
        Schedule schedule = new Schedule();
        schedule.setId(1);
        schedule.setUsername("admin");
        schedule.setYear(2007);
        schedule.setMonth(6);
        schedule.setDay(30);
        schedule.setPlan("JavaWeb");
        scheduleService.update(schedule);

        Schedule schedule2 = scheduleService.select(1);
        assertTrue(schedule2.getMonth() ==6);
    }
}