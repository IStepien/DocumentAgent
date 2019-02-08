package com.istepien.service;

import com.istepien.dao.RoleDao;
import com.istepien.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);


    @Autowired
    RoleDao roleDao;

    @Override
    @Transactional
    public Set<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    @Transactional
    public Role getRoleByName(String roleName) {
        return roleDao.getRoleByName(roleName);
    }
}
