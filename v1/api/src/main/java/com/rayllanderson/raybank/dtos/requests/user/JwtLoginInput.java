package com.rayllanderson.raybank.dtos.requests.user;

import lombok.Data;

@Data
public class JwtLoginInput {
    private String username;
    private String password;
}