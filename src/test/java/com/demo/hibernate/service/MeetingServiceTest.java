package com.demo.hibernate.service;

import com.demo.hibernate.beans.Meeting;
import com.demo.hibernate.dao.MeetingDaoImpl;
import com.demo.hibernate.util.Page;
import com.demo.hibernate.dao.MeetingDao;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MeetingServiceTest {

    private MeetingService meetingService;

    @Before
    public void setUp() {
        MeetingDao meetingDao = new MeetingDaoImpl();
        meetingService = new MeetingServiceImpl(meetingDao);
    }

    @Test
    public void list() {
        Page page = meetingService.list(25, 1);
        assertTrue(page.getRowCount() > 0);
        for (Object o : page.getResultList()) {
            Meeting address = (Meeting) o;
            assertNotNull(address);
        }
    }

    @Test
    public void select() {
        assertNotNull(meetingService.select(1));
    }

    @Test
    public void insert() {
        Meeting meeting = new Meeting();
        meeting.setSender("admin");
        meeting.setStarttime("2008-10-10");
        meeting.setEndtime("2008-10-12");
        meeting.setAddress("Beijing");
        meeting.setTitle("JavaWeb");
        meeting.setContent("JavaWeb");
        assertNotNull(meetingService.insert(meeting));
    }

    @Test
    public void update() {
        Meeting meeting = new Meeting();
        meeting.setId(1);
        meeting.setSender("admin");
        meeting.setStarttime("2008-10-10");
        meeting.setEndtime("2008-10-12");
        meeting.setAddress("Shanghai");
        meeting.setTitle("JavaWeb");
        meeting.setContent("JavaWeb");
        meetingService.update(meeting);

        Meeting meeting2 = meetingService.select(1);
        assertTrue(meeting2.getAddress().equals("Shanghai"));
    }
}