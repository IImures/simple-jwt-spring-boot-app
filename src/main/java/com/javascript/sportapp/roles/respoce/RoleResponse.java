package com.javascript.sportapp.roles.respoce;

import com.javascript.sportapp.roles.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleResponse {
    private String authority;

    public RoleResponse(Role role){
        this.authority = role.getAuthority();
    }
}
