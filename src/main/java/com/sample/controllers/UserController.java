package com.sample.controllers;

import com.sample.entities.User;
import com.sample.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    UserService userService;

    @Autowired
    private void setUserService(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public String createUser(@RequestParam String username, @RequestParam String password){

        userService.createUser(new User(username, password));
        return "redirect:/";
    }


}
