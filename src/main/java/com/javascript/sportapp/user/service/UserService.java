package com.javascript.sportapp.user.service;

import com.javascript.sportapp.configuration.JwtService;
import com.javascript.sportapp.roles.entity.Role;
import com.javascript.sportapp.roles.repository.RoleRepository;
import com.javascript.sportapp.roles.respoce.RoleResponse;
import com.javascript.sportapp.user.controller.request.AuthenticationRequest;
import com.javascript.sportapp.user.controller.request.UserRequest;
import com.javascript.sportapp.user.controller.response.AuthenticationResponse;
import com.javascript.sportapp.user.controller.response.UserResponse;
import com.javascript.sportapp.user.entity.User;
import com.javascript.sportapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse createUser(UserRequest details){
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
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

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
        String jwtToken = jwtService.generateToken(saved);

        return new AuthenticationResponse(jwtToken);
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
                (
                request.getLogin(),
                request.getPassword()
        );
        authenticationManager.authenticate( token ).isAuthenticated();
        if(token.isAuthenticated()) System.out.println("wefawerfawergfewrafgwergweagerwgerg");
        User user = userRepository.findUserByLogin(request.getLogin())
                .orElseThrow(()-> new UsernameNotFoundException("User was not found"));

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    @Transactional
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);

        response.setRoles(
                user.getRoles().stream().map((RoleResponse::new)).toArray(RoleResponse[]::new)
        );
        return response;
    }
}
