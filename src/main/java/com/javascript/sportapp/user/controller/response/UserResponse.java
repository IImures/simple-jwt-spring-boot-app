package com.javascript.sportapp.user.controller.response;

import com.javascript.sportapp.roles.respoce.RoleResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private RoleResponse[] roles;

}
