package com.demo.hibernate.service;

import com.demo.hibernate.beans.Notice;
import com.demo.hibernate.util.Page;
import com.demo.hibernate.dao.NoticeDao;
import com.demo.hibernate.dao.NoticeDaoImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoticeServiceTest {

    private NoticeService noticeService;

    @Before
    public void setUp() {
        NoticeDao noticeDao = new NoticeDaoImpl();
        noticeService = new NoticeServiceImpl(noticeDao);
    }

    @Test
    public void list() {
        Page page = noticeService.list(25, 1);
        assertTrue(page.getRowCount() > 0);
        for (Object o : page.getResultList()) {
            Notice address = (Notice) o;
            assertNotNull(address);
        }
    }

    @Test
    public void select() {
        assertNotNull(noticeService.select(1));
    }

    @Test
    public void insert() {
        Notice notice = new Notice();
        notice.setSender("admin");
        notice.setTitle("JavaWeb");
        notice.setContent("JavaWeb");
        notice.setSendtime("2008-10-10");
        assertNotNull(noticeService.insert(notice));
    }

    @Test
    public void update() {
        Notice notice = new Notice();
        notice.setId(1);
        notice.setSender("admin");
        notice.setTitle("Hibernate");
        notice.setContent("JavaWeb");
        notice.setSendtime("2008-10-10");
        noticeService.update(notice);

        Notice notice2 = noticeService.select(1);
        assertTrue(notice2.getTitle().equals("Hibernate"));
    }
}