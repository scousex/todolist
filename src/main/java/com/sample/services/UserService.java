package com.sample.services;

import com.sample.entities.User;

public interface UserService {
    void createUser(User user);
    User findUserByUsername(String username);
}
