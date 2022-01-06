package com.example.challengebackendjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

  @NotBlank(message = "El nombre es obligatorio")
  private String name;

  @NotBlank(message = "El username es obligatorio")
  private String username;

  @NotBlank(message = "El password es obligatorio")
  private String password;

  @NotBlank(message = "El email es obligatorio")
  private String email;

  @ManyToMany(fetch = FetchType.EAGER)
  private Collection<Role> roles = new ArrayList<>();
}
