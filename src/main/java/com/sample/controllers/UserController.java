package com.sample.controllers;

import com.sample.entities.User;
import com.sample.services.SecurityService;
import com.sample.services.UserService;
import com.sample.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(path="/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping(path="/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model){
        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors()){
            return "registration";
        }
        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());

        return "redirect:/todos";
    }

    @GetMapping(path="/login")
    public String login(Model model, String errors, String logout){

        if(errors!=null){
            model.addAttribute("error", "Вы ввели неверные имя пользователя или пароль");
        }

        if(logout!=null){
            model.addAttribute("message", "Вы успешно разлогинились!");
        }

        return "login";
    }







}
