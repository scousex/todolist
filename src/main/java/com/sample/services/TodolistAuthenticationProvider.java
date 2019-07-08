package com.sample.services;

import com.sample.entities.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class TodolistAuthenticationProvider implements AuthenticationProvider  {

    public static final Logger logger = Logger.getLogger(TodolistAuthenticationProvider.class.getName());

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        logger.info("Authenticating");


        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if(userRepository.findByUsername(username).getPassword().equals(password)){
            return new UsernamePasswordAuthenticationToken(username,password,grantedAuthorities);
        }


        else return null;


    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
