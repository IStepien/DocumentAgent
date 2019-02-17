package com.istepien.dao;

import com.istepien.model.Role;
import com.istepien.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<User> getAllUsers() {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        List<User> userList = sessionObj.createQuery("from User").list();
        sessionObj.getTransaction().commit();
        for (User user : userList) {
            logger.info("User list: " + user);
        }
        sessionObj.close();
        return userList;
    }

    @Override
    public void saveUser(User user) {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        sessionObj.save(user);
        sessionObj.getTransaction().commit();
        logger.info("User saved successfully, user details=" + user);
        sessionObj.close();

    }

    @Override
    public User getUser(Long id) {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        User user = sessionObj.load(User.class, new Long(id));
        sessionObj.getTransaction().commit();
        logger.info("User loaded successfully, user details=" + user);
        sessionObj.close();

        return user;
    }

    @Override
    public User getUserByName(String username) {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        User user = (User) sessionObj
                .createQuery("from User where username=:username")
                .setParameter("username", username)
                .getSingleResult();
        sessionObj.getTransaction().commit();
        logger.info("User found successfully, user details=" + user);
        sessionObj.close();

        return user;
    }

    @Override
    public void deleteUser(Long id) {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        User user = sessionObj.load(User.class, new Long(id));
        if (user != null) {
            sessionObj.delete(user);
        }
        sessionObj.getTransaction().commit();
        logger.info("User deleted successfully, user details=" + user);
        sessionObj.close();


    }

    @Override
    public void updateUser(User user) {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        sessionObj.update(user);
        sessionObj.getTransaction().commit();
        logger.info("User updated successfully, user details=" + user);
        sessionObj.close();


    }

    @Override
    public Set<Role> getUserRoles(Long userId) {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        List<Role> roleList = sessionObj.createQuery("select ur from User u inner join u.roles ur where u.userId=:userId").list();
        Set<Role> roleSet = new HashSet<>();
        roleSet.addAll(roleList);
        sessionObj.close();

        return roleSet;
    }

}
