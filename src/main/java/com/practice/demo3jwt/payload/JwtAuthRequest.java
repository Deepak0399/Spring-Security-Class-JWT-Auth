package com.practice.demo3jwt.payload;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;
    private String password;
}
