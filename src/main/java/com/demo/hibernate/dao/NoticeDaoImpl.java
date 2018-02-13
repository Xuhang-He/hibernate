package com.demo.hibernate.dao;

import com.demo.hibernate.beans.Notice;
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

public class NoticeDaoImpl implements NoticeDao {
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
            CriteriaQuery<Notice> criteriaQuery = builder.createQuery(Notice.class);

            Root<Notice> root  = criteriaQuery.from(Notice.class);
            criteriaQuery.orderBy(builder.desc(root.get("sendtime")));
            Query<Notice> query = session.createQuery(criteriaQuery)
                    .setFirstResult(startIndex)
                    .setMaxResults(pageSize);

            List<Notice> result = query.getResultList();
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
    public Optional<Notice> select(Integer id) {
        Notice record;
        try {
            Session session = HibernateSessionFactory.currentSession();
            Query query = session.createQuery("from Notice where id=?");
            query.setParameter(0, id);
            record = (Notice) query.uniqueResult();
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
    public Integer insert(Notice record) {
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
    public boolean update(Notice record) {
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
    public boolean delete(Notice record) {
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
