package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.model.User;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class UserRepository extends Repositorio<User> {

  public User findByUsername(String username) {
    return elementos.stream()
        .filter(user -> Objects.equals(user.getUsername(), username))
        .findFirst()
        .orElse(null);
  }

  public User findByEmail(String email) {
    return elementos.stream()
        .filter(user -> Objects.equals(user.getEmail(), email))
        .findFirst()
        .orElse(null);
  }
}
