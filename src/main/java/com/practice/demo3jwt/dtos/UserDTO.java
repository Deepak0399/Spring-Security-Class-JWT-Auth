package com.practice.demo3jwt.dtos;

import lombok.Data;

@Data
public class UserDTO {

    private String username;
    private String password;
    private String role;
}
