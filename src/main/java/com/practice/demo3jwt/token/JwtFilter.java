package com.practice.demo3jwt.token;

import com.practice.demo3jwt.services.MyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtToken jwtToken;
    private final MyService userDetail;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
            if (!jwtToken.isTokenExpired(token)) {
                String username = jwtToken.getUsernameFromToken(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetail.loadUserByUsername(username);
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken up = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(up);
                    }
                } else {
                    System.out.println("Username Not Found");
                }
            } else {
                System.out.println("token is expired");
            }
        }
        filterChain.doFilter(request, response);
    }
}
