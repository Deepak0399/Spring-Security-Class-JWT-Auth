package com.practice.demo3jwt.repo;

import com.practice.demo3jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
}
