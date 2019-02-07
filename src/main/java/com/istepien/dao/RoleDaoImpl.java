package com.istepien.dao;

import com.istepien.model.Role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class RoleDaoImpl implements RoleDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Role> getAllRoles() {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        List<Role> roles = sessionObj.createQuery("from Role").list();
        sessionObj.getTransaction().commit();
        for (Role role : roles) {
            logger.info("Role list: " + role);
        }
        return roles;
    }

    @Override
    public Role getRoleByName(String roleName) {
        Session sessionObj =  sessionFactory.openSession();
        sessionObj.beginTransaction();
        Role theRole = (Role) sessionObj.load(Role.class, roleName);
        sessionObj.getTransaction().commit();
        logger.info("Role loaded successfully, role details=" + theRole);
        return theRole;      }

}
