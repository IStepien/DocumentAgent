package com.istepien.dao;

import com.istepien.model.Document;
import com.istepien.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(DocumentDaoImpl.class);
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public List<User> getAlUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> userList = session.createQuery("from User").list();
        for (User user : userList) {
            logger.info("User list: " + user);
        }
        return userList;
    }

    @Override
    public void saveUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
        logger.info("User saved successfully, user details=" + user);

    }

    @Override
    public User getUser(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, new Long(id));
        logger.info("User loaded successfully, user details=" + user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, new Long(id));
        if (user != null) {
            session.delete(user);
        }
        logger.info("User deleted successfully, user details=" + user);

    }

    @Override
    public void updateUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(user);
        logger.info("User updated successfully, user details=" + user);

    }
}
