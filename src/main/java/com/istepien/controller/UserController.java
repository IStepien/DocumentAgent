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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @GetMapping("/allUsers")
    public String listUsers(Model model, Principal principal) {

        List<User> userList = userService.getAllUsers();
        userList.removeIf(user -> user.getRole().getRolename().equals("ROLE_ADMIN"));

        User currentUser = userService.getUserByName(principal.getName());
        if (currentUser.getRole().getRolename().equals("ROLE_MODERATOR")) {
            userList = userList.stream().filter(u -> u.getRole().getRolename().equals("ROLE_USER")).collect(Collectors.toList());
        }

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


    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam(name = "userId") Long userId) {
        userService.deleteUser(userId);

        return "home";
    }

    @GetMapping("/changeRole")
    public String changeRole(@RequestParam(name = "userId") Long userId) {
        User currentUser = userService.getUser(userId);
        Role currentUserRole = currentUser.getRole();


        if (currentUserRole.getRolename().equals("ROLE_MODERATOR")){
            currentUser.setRole(roleService.getRoleByName("ROLE_USER"));
        } else {
            currentUser.setRole(roleService.getRoleByName("ROLE_MODERATOR"));
        }


        userService.updateUser(currentUser);


        return "home";

    }

}
