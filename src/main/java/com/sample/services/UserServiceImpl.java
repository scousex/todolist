package com.sample.services;

import com.sample.entities.User;
import com.sample.entities.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository repository;

    @Override
    public User findUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUser(User user) {
        repository.createUser(user.getUsername(), user.getPassword());
    }
}
