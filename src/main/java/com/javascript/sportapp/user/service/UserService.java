package com.javascript.sportapp.user.service;

import com.javascript.sportapp.roles.entity.Role;
import com.javascript.sportapp.roles.repository.RoleRepository;
import com.javascript.sportapp.roles.respoce.RoleResponse;
import com.javascript.sportapp.user.controller.request.UserRequest;
import com.javascript.sportapp.user.controller.response.UserResponse;
import com.javascript.sportapp.user.entity.User;
import com.javascript.sportapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    //todo change exception to my own
    public User loadUserByUsername(String username) {
        return userRepository.findUserByLogin(username).orElseThrow(()->new RuntimeException("User not found"));
    }

    public UserResponse createUser(UserRequest details){
        boolean isExists = false; //todo change to true back!!!!
        try {
            userRepository.findUserByLogin(details.getLogin()).orElseThrow(()-> new RuntimeException("User does not exists"));
        } catch (RuntimeException e){
            isExists = false;
        }
        if(isExists) throw new RuntimeException("User already exists with this login");

        User newUser = new User();

        BeanUtils.copyProperties(details, newUser);
        newUser.setIsEnabled(true);

        Set<Role> roles = new HashSet<>();
        for(Long roleId : details.getRoles()){
            roles.add(
                    roleRepository
                            .findById(roleId)
                            .orElseThrow(()-> new RuntimeException("Role " + roleId + " does not exists!"))
            );
        }

        newUser.setRoles(roles);

        User saved = userRepository.save(newUser);
        UserResponse response =  new UserResponse();
        BeanUtils.copyProperties(saved, response);
        response.setRoles(
                saved.getRoles().stream().map((RoleResponse::new)).toArray(RoleResponse[]::new)
        );
        return response;
    }
}
