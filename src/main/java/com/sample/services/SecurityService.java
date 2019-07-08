package com.sample.services;

import com.sample.entities.Note;

import java.util.List;

public interface SecurityService {

    String findUserInUsername();

    String autoLogin(String username, String password);

    void logout();
}
