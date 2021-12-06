package com.example.challengebackendjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data @NoArgsConstructor @AllArgsConstructor
public class User extends Entidad{
  private String name;
  private String username;
  private String password;
  private String email;
  private Collection<Role> roles = new ArrayList<>();

  @Override
  public void update(Entidad entidad) {
    User user = (User) entidad;
    name = user.name;
    username = user.username;
    password = user.password;
  }

  @Override
  public boolean esValido() {
    return true;
  }
}
