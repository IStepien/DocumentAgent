package com.istepien.dao;

import com.istepien.model.User;

import java.util.List;

public interface UserDao {
    public List<User> getAlUsers();
    public void saveUser(User user);
    public User getUser(Long id);
    public void deleteUser(Long id);
}
