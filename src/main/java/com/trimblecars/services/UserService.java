package com.trimblecars.services;


import com.trimblecars.model.Users;
import com.trimblecars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register a new user
    public Users registerUser(Users user) {
        return userRepository.save(user);
    }

    // Get user by ID
    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Get all users
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
}