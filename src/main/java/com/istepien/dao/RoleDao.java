package com.istepien.dao;

import com.istepien.model.Role;

import java.util.List;

public interface RoleDao {
    public List<Role> getAllRoles();
    public Role getRoleByName(String roleName);

}
