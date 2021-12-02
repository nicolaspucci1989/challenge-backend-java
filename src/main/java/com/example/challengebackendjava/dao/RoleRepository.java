package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.model.Role;

import java.util.Objects;

public class RoleRepository extends Repositorio<Role> {
  Role findByName(String name) {
    return elementos
        .stream()
        .filter(role -> Objects.equals(role.getName(), name))
        .findFirst()
        .orElse(null);
  }
}
