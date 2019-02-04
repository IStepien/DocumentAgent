package com.istepien.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Home {

    @RequestMapping("/")
    public String goHome(){

        return "home-page";
    }



}
