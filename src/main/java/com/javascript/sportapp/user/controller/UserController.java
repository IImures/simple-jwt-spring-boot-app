package com.javascript.sportapp.user.controller;

import com.javascript.sportapp.user.controller.request.AuthenticationRequest;
import com.javascript.sportapp.user.controller.request.UserRequest;
import com.javascript.sportapp.user.controller.response.AuthenticationResponse;
import com.javascript.sportapp.user.controller.response.UserResponse;
import com.javascript.sportapp.user.entity.User;
import com.javascript.sportapp.user.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUser(
            @RequestBody UserRequest request
    ){
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }


    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        System.out.println("validation");
        return new ResponseEntity<>(userService.authenticate(request), HttpStatus.OK);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable Long userId
    ){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

}
