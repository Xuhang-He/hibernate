package com.demo.hibernate.dao;

import com.demo.hibernate.beans.Meeting;
import com.demo.hibernate.util.Page;
import com.demo.hibernate.util.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeetingDaoImpl implements MeetingDao {
    @Override
    public Page list(int pageSize, int pageNo) {
        Session session;
        Page page;

        // 取得当前页
        int startIndex = pageSize * (pageNo - 1);

        // 创建查询条件
        try {
            session = HibernateSessionFactory.currentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Meeting> criteriaQuery = builder.createQuery(Meeting.class);

            Root<Meeting> root  = criteriaQuery.from(Meeting.class);
            criteriaQuery.orderBy(builder.desc(root.get("starttime")));
            criteriaQuery.orderBy(builder.desc(root.get("endtime")));
            Query<Meeting> query = session.createQuery(criteriaQuery)
                    .setFirstResult(startIndex)
                    .setMaxResults(pageSize);

            List<Meeting> result = query.getResultList();
            // 取得总数
            int rowCount = result.size();
            page = new Page(pageSize, pageNo, rowCount, result);
            session.close();
        } catch (HibernateException e) {
            page = new Page(pageSize, pageNo, 0, new ArrayList<>());
            throw e;
        } finally {
            HibernateSessionFactory.closeSession();
        }
        return page;
    }

    @Override
    public Optional<Meeting> select(Integer id) {
        Meeting record;
        try {
            Session session = HibernateSessionFactory.currentSession();
            Query query = session.createQuery("from Meeting where id=?");
            query.setParameter(0, id);
            record = (Meeting) query.uniqueResult();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        } finally {
            HibernateSessionFactory.closeSession();
        }
        return Optional.ofNullable(record);
    }

    @Override
    public Integer insert(Meeting record) {
        try {
            Session session = HibernateSessionFactory.currentSession();
            Transaction transaction = session.beginTransaction();
            session.save(record);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw e;
        } finally {
            HibernateSessionFactory.closeSession();
        }
        return record.getId();
    }

    @Override
    public boolean update(Meeting record) {
        try {
            Session session = HibernateSessionFactory.currentSession();
            Transaction transaction = session.beginTransaction();
            session.update(record);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        } finally {
            HibernateSessionFactory.closeSession();
        }
        return true;
    }

    @Override
    public boolean delete(Meeting record) {
        try {
            Session session = HibernateSessionFactory.currentSession();
            Transaction transaction = session.beginTransaction();
            session.delete(record);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        } finally {
            HibernateSessionFactory.closeSession();
        }
        return true;
    }
}
