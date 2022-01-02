package com.example.challengebackendjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String name;
  private String username;
  private String password;
  private String email;

  @ManyToMany(fetch = FetchType.EAGER)
  private Collection<Role> roles = new ArrayList<>();

  public void update(User user) {
    name = user.name;
    username = user.username;
    password = user.password;
  }

  public boolean esValido() {
    return true;
  }
}
