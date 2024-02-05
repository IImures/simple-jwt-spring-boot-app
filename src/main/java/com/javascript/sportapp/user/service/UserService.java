package com.javascript.sportapp.user.service;

import com.javascript.sportapp.user.entity.User;
import com.javascript.sportapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //todo change exception to my own
    public User loadUserByUsername(String username) {
        return userRepository.findUserByLogin(username).orElseThrow(()->new RuntimeException("User not found"));
    }
}
