package com.demo.hibernate.dao;

import com.demo.hibernate.beans.Schedule;
import com.demo.hibernate.util.HibernateSessionFactory;
import com.demo.hibernate.util.Page;
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

public class ScheduleDaoImpl implements ScheduleDao {

    @Override
    public Page list(String username, int pageSize, int pageNo) {

        Session session;
        Page page;

        // 取得当前页
        int startIndex = pageSize * (pageNo - 1);

        // 创建查询条件
        try {
            session = HibernateSessionFactory.currentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Schedule> criteriaQuery = builder.createQuery(Schedule.class);

            Root<Schedule> root  = criteriaQuery.from(Schedule.class);
            criteriaQuery.select(root).where(builder.equal(root.get("username"), username));
            criteriaQuery.orderBy(builder.desc(root.get("year")));
            criteriaQuery.orderBy(builder.desc(root.get("month")));
            criteriaQuery.orderBy(builder.desc(root.get("day")));
            Query<Schedule> query = session.createQuery(criteriaQuery)
                    .setFirstResult(startIndex)
                    .setMaxResults(pageSize);

            List<Schedule> result = query.getResultList();
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
    public Optional<Schedule> select(Integer id) {
        Schedule record;
        try {
            Session session = HibernateSessionFactory.currentSession();
            Query query = session.createQuery("from Schedule where id=?");
            query.setParameter(0, id);
            record = (Schedule) query.uniqueResult();
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
    public Integer insert(Schedule record) {
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
    public boolean update(Schedule record) {
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
    public boolean delete(Schedule record) {
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
