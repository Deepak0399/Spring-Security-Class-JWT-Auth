package com.practice.demo3jwt.controller;

import com.practice.demo3jwt.payload.JwtAuthRequest;
import com.practice.demo3jwt.payload.JwtAuthResponse;
import com.practice.demo3jwt.token.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequiredArgsConstructor
public class MyController {

    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest jwtAuthRequest) {

        String username = jwtAuthRequest.getUsername();
        String password = jwtAuthRequest.getPassword();
        UserDetails userDetails = authenticate(username, password);
        // Generate token on the basis of userDetails object
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        String token = jwtToken.generateToken(userDetails);
        jwtAuthResponse.setToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(jwtAuthResponse);

    }
    public UserDetails authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken userpass = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(userpass);
        return (UserDetails) authenticate.getPrincipal();
    }
    @GetMapping("/member")
    public void member() {

    }
    @GetMapping("admin")
    public void admin() {

    }
}
