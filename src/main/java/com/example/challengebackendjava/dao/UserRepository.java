package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.model.User;

import java.util.Objects;

public class UserRepository extends Repositorio<User> {
  User findByUsername(String username) {
    return elementos.stream()
        .filter(user -> Objects.equals(user.getUsername(), username))
        .findFirst()
        .orElse(null);
  }
}
