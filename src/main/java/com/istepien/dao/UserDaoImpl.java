package com.istepien.dao;

import com.istepien.model.Document;
import com.istepien.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private SessionFactory session;


    @Override
    public List<User> getAllUsers() {
        List<User> userList = session.openSession().createQuery("from User").list();
        for (User user : userList) {
            logger.info("User list: " + user);
        }
        return userList;
    }

    @Override
    public void saveUser(User user) {
        session.openSession().save(user);
        logger.info("User saved successfully, user details=" + user);

    }

    @Override
    public User getUser(Long id) {
        User user = (User) session.openSession().load(User.class, new Long(id));
        logger.info("User loaded successfully, user details=" + user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        User user = (User) session.openSession().load(User.class, new Long(id));
        if (user != null) {
            session.openSession().delete(user);
        }
        logger.info("User deleted successfully, user details=" + user);

    }

    @Override
    public void updateUser(User user) {
        session.openSession().update(user);
        logger.info("User updated successfully, user details=" + user);

    }
}
