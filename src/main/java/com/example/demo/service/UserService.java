package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder; 
    
    public User registerUser(User user) {
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        
        return userRepository.save(user);
    }

    // Método utilitário para checar se o usuário existe 
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}