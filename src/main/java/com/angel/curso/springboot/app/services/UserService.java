package com.angel.curso.springboot.app.services;

import com.angel.curso.springboot.app.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User save(User user);

    boolean existsByUsername(String username);
}
