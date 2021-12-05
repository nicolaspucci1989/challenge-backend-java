package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.model.Role;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class RoleRepository extends Repositorio<Role> {
  public Role findByName(String name) {
    return elementos
        .stream()
        .filter(role -> Objects.equals(role.getName(), name))
        .findFirst()
        .orElse(null);
  }
}
