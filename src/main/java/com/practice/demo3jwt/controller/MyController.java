package com.practice.demo3jwt.controller;

import com.practice.demo3jwt.payload.JwtAuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequiredArgsConstructor
public class MyController {

    private final AuthenticationManager authenticationManager;
    @GetMapping("/login")
    public void login(@RequestBody JwtAuthRequest jwtAuthRequest) {

        String username = jwtAuthRequest.getUsername();
        String password = jwtAuthRequest.getPassword();
       authenticate(username, password);
    }
    public void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    @GetMapping("/member")
    public void member() {

    }
    @GetMapping("admin")
    public void admin() {

    }
}
