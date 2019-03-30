package com.istepien.service;

import com.istepien.dao.UserDao;
import com.istepien.model.Role;
import com.istepien.model.User;
import com.sun.deploy.security.BlockedException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException   {

        User user = userDao.getUserByName(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        if(user.getIsBlocked() == true){
            throw new InternalAuthenticationServiceException("Blocked user");
        }


        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getRolename()));



        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
