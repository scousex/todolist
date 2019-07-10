package com.sample.services;

import com.sample.entities.Note;
import com.sample.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SecurityServiceImpl implements SecurityService, AuthenticationProvider {

    public static final Logger logger  = Logger.getLogger(SecurityServiceImpl.class.getName());

    //private TokenProvider tokenProvider;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Funcition to find users todolist after authorization
     * @return List of Notes (TodoList)
     */
    @Override
    public String findUserInUsername() {

        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        logger.info("SecurityContextHolder info: "
              + userDetails.toString());

        if(userDetails != null){
             return (String) userDetails;
        }

        logger.info("Userdetails is null");

        return "NOBODY";
    }

    /**
     * Autologin
     * @param username
     * @param password
     */
    @Override
    public Authentication autoLogin(String username, String password) {

        logger.info("AutoLOgin function is working..");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        logger.info("Got userDetailService for user " + userDetails.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                                        userDetails.getPassword(), userDetails.getAuthorities());
        logger.info("Authentication Token was created: "+ usernamePasswordAuthenticationToken.toString());


        if(usernamePasswordAuthenticationToken.isAuthenticated()){
            logger.info("User is authenticated with token");
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        }
      //  logger.info("TOkEN: " + usernamePasswordAuthenticationToken.getDetails().toString());
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
        //authenticationManager.authenticate(null);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        logger.info("AutoLOgin function is working..");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        logger.info("Got userDetailService for user " + userDetails.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                        userDetails.getPassword(), userDetails.getAuthorities());
        logger.info("Authentication Token was created: "+ usernamePasswordAuthenticationToken.toString());


        if(usernamePasswordAuthenticationToken.isAuthenticated()){
            logger.info("User is authenticated with token");
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        logger.info("TOkEN: " + usernamePasswordAuthenticationToken.toString());
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
