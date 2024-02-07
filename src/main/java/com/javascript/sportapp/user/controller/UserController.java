package com.javascript.sportapp.user.controller;

import com.javascript.sportapp.user.controller.request.AuthenticationRequest;
import com.javascript.sportapp.user.controller.request.UserRequest;
import com.javascript.sportapp.user.controller.response.AuthenticationResponse;
import com.javascript.sportapp.user.controller.response.UserResponse;
import com.javascript.sportapp.user.entity.User;
import com.javascript.sportapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUser(
            @RequestBody UserRequest request
    ){
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return new ResponseEntity<>(userService.authenticate(request), HttpStatus.OK);
    }

    @PostMapping( value = "/authenticate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<AuthenticationResponse> someControllerMethod( @RequestParam Map<String, String> body ) {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setLogin(body.get("login"));
        request.setPassword(body.get("password"));
        return new ResponseEntity<>(userService.authenticate(request), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable Long userId
    ){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

}
