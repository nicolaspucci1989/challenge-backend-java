package com.example.challengebackendjava.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Genero {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String nombre;

  private String imagen;

  @OneToMany(fetch = LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "genero")
  private Set<GeneroPeliculaSerie> peliculasSeries = new HashSet<>();

  public Genero(String nombre, String imagen) {
    this.nombre = nombre;
    this.imagen = imagen;
  }

  public void agregarPelicula(PeliculaSerie peliculaSerie) {
    GeneroPeliculaSerie generoPeliculaSerie = new GeneroPeliculaSerie(this, peliculaSerie);
    this.peliculasSeries.add(generoPeliculaSerie);
  }

}
