package com.istepien.dao;

import com.istepien.model.Role;

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
public class RoleDaoImpl implements RoleDao {

    private static final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Set<Role> getAllRoles() {
        Set<Role> roles = new HashSet<>();
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        List<Role> roleList = sessionObj.createQuery("from Role").list();
        roles.addAll(roleList);
        sessionObj.getTransaction().commit();
        for (Role role : roles) {
            logger.info("Role list: " + role);
        }
        sessionObj.close();

        return roles;
    }

    @Override
    public Role getRoleByName(String rolename) {
        Session sessionObj = sessionFactory.openSession();
        sessionObj.beginTransaction();
        Role theRole = (Role) sessionObj
                .createQuery("from Role where rolename = :rolename")
                .setParameter("rolename", rolename)
                .getSingleResult();

        sessionObj.getTransaction().commit();
        logger.info("Role loaded successfully, role details=" + theRole.getRolename() + theRole.getRoleId() + theRole.getUsers());
        sessionObj.close();

        return theRole;
    }

}
