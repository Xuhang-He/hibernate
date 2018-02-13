package com.demo.hibernate.dao;

import com.demo.hibernate.beans.User;
import com.demo.hibernate.util.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;


public class UserDaoImpl implements UserDao {

    @Override
    public Optional<User> select(String username) {
        Session session;
        User record;

        try {
            session = HibernateSessionFactory.currentSession();
            Query query = session.createQuery("from User where username=?");
            query.setParameter(0, username.trim());
            record = (User) query.uniqueResult();
            session.close();
        } catch (HibernateException e) {
            throw e;
        } finally {
            HibernateSessionFactory.closeSession();
        }
        return Optional.ofNullable(record);
    }

    @Override
    public Integer insert(User userRecord) {
        try {
            Session session = HibernateSessionFactory.currentSession();
            Transaction transaction = session.beginTransaction();
            session.save(userRecord);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw e;
        } finally {
            HibernateSessionFactory.closeSession();
        }
        return userRecord.getId();
    }
}
