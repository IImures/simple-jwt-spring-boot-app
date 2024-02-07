package com.javascript.sportapp.user.controller;

import com.javascript.sportapp.user.controller.request.UserRequest;
import com.javascript.sportapp.user.controller.response.UserResponse;
import com.javascript.sportapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody UserRequest request
            ){
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

}
