package com.example.challengebackendjava.model;

import com.example.challengebackendjava.serializer.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.example.challengebackendjava.model.Helper.stringsCoinciden;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class Personaje {
  @Id @GeneratedValue(strategy = AUTO)
  @JsonView(View.Personaje.Lista.class)
  private Long id;
  @JsonView(View.Personaje.Lista.class)
  String imagen;
  @JsonView(View.Personaje.Lista.class)
  String nombre;
  Integer edad;
  String historia;
  Float peso;
  @ManyToMany
  Set<PeliculaSerie> peliculaSerieSet = new HashSet<>();

  public boolean nombreCoincide(String nombre) {
    return stringsCoinciden(getNombre(), nombre);
  }

  public boolean edadCoincide(Integer edad) {
    return Objects.equals(getEdad(), edad);
  }

  //  Set<PeliculaSerie> peliculasSeries = new HashSet<>();
}
