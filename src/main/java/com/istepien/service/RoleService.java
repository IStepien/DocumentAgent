package com.istepien.service;

import com.istepien.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    public Set<Role> getAllRoles();
    public Role getRoleByName(String roleName);
}
