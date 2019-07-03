package com.sample.services;

import com.sample.entities.Note;

import java.util.List;

public interface SecurityService {

    String findUserInUsername();

    void autoLogin(String username, String password);
}
