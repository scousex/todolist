package com.sample.services;

import com.sample.entities.User;

public interface UserService {

    User findByUsername(String username);
    void save(User user);
}
