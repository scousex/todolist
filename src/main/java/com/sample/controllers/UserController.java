package com.sample.controllers;

import com.google.gson.JsonObject;
import com.sample.entities.User;
import com.sample.services.SecurityService;
import com.sample.services.UserService;
import com.sample.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

@RestController
public class UserController {

    public static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

   // @Autowired
    //private UserValidator userValidator;


    @PostMapping(path="/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registration(@RequestBody Login login){

        String token = "Token is not generated, please sign in using /login";
        logger.info("Registration started");
        User user = new User(login.getUsername(), login.getPassword());

       // userValidator.validate(user, bindingResult);

        if(userService.findByUsername(user.getUsername())!=null){
            logger.info("Response has errors");
            return new ResponseEntity<Object>("This user already exists",HttpStatus.BAD_REQUEST);
        }
        userService.save(user);

        logger.info("AutoLogin started");
        try {
            token = securityService.autoLogin(user.getUsername(), user.getPassword());
        } catch (Exception e){
            logger.info(e.getMessage());
        }

        return new ResponseEntity<Object>(token, HttpStatus.OK);
    }


    @CrossOrigin("/*")
    @PostMapping(path="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> login(@RequestBody Login login){

        String token;

        logger.info("Login started");
        if(userService.findByUsername(login.getUsername())==null){
            logger.info("Username is not found");
           return new ResponseEntity<Object>("User does not exists", HttpStatus.BAD_REQUEST);
        }
        logger.info("AutoLogin started");


        token = securityService.autoLogin(login.getUsername(),login.getPassword());


        return new ResponseEntity<Object>(token, HttpStatus.OK);
    }

   /* @GetMapping(path="/logout")
    public ResponseEntity<JsonObject> logout()
    {
        securityService.logout();
        return new ResponseEntity<JsonObject>(HttpStatus.OK);
    }
*/
}
