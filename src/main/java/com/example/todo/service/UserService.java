package com.example.todo.service;

import com.example.todo.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    User save(User user);
    Optional<User> findById(int id);
    boolean delete(int id, int userId);
}
