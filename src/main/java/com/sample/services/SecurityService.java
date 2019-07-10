package com.sample.services;

import com.sample.entities.Note;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface SecurityService {

    String findUserInUsername();

    Authentication autoLogin(String username, String password);

    void logout();
}
