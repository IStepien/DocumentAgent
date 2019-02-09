package com.istepien.controller;


import com.istepien.model.User;
import com.istepien.service.RoleService;
import com.istepien.service.SecurityServiceImpl;
import com.istepien.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private final SecurityServiceImpl securityServiceImpl;

    public LoginController(UserService userService, SecurityServiceImpl securityServiceImpl) {
        this.userService = userService;
        this.securityServiceImpl = securityServiceImpl;
    }

    @GetMapping("/showLoginPage")
    public String showLoginPage() {

        return "login-page";
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user, Model model) {
        userService.registerNewUserAccount(user);
        securityServiceImpl.login(user.getUsername(), user.getPassword());

        return "home";
    }

}
