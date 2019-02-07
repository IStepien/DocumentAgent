package com.istepien.dao;

import com.istepien.model.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUsers();
    public void saveUser(User user);
    public User getUser(Long id);
    public User getUserByName(String userName);
    public void deleteUser(Long id);
    public void updateUser(User user);
}
