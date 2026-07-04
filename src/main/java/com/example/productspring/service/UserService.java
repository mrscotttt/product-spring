package com.example.productspring.service;

import com.example.productspring.exception.UserNotFoundException;
import com.example.productspring.model.User;
import com.example.productspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        getUserById(id);
        return userRepository.update(id, user);
    }

    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.delete(id);
    }
}
