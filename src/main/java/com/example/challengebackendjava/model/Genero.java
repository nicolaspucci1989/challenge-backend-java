package com.example.challengebackendjava.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
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

  @ManyToMany(fetch = LAZY)
  private Set<PeliculaSerie> peliculasSeries = new HashSet<>();

  public Genero(String nombre, String imagen) {
    this.nombre = nombre;
    this.imagen = imagen;
  }

  public void agregarPelicula(PeliculaSerie peliculaSerie) {
    peliculasSeries.add(peliculaSerie);
  }

  public void eliminarPeliculaSerie(PeliculaSerie peliculaSerie) {
    peliculasSeries.remove(peliculaSerie);
  }

  public boolean esValido() {
    return tieneNombreVaido();
  }

  private boolean tieneNombreVaido() {
    return nombre.length() > 1;
  }

  public boolean tienePelicula(PeliculaSerie peliculaSerie) {
    return this.peliculasSeries
        .stream().anyMatch(peli -> Objects.equals(peli.getId(), peliculaSerie.getId()));
  }
}
