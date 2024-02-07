package com.javascript.sportapp.login;

import com.javascript.sportapp.login.request.LoginRequest;
import com.javascript.sportapp.security.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/login")
public class Login {

    @PostMapping
    public String login(@RequestBody LoginRequest request){
        String token = JwtUtil.generateToken(request.getUsername());
        return token;
    }
}
