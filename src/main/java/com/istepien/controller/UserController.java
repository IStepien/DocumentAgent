package com.istepien.controller;


import com.istepien.model.Role;
import com.istepien.model.User;
import com.istepien.service.RoleService;
import com.istepien.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @GetMapping("/allUsers")
    public String listUsers(Model model) {

        List<User> userList = userService.getAllUsers();

        model.addAttribute("userList", userList);

        return "user-list";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        User user = new User();

        theModel.addAttribute("user", user);

        return "create-user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "user-list";
    }


//    public User getUser(Long id);
//
//    public void deleteUser(Long id);
//
//    public void updateUser(User user);


}
