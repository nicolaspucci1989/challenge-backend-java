package com.example.challengebackendjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Personaje {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @NotBlank(message = "La imagen es obligatoria")
  private String imagen;

  @NotBlank(message = "El nombre es obligatorio")
  private String nombre;

  @NotNull(message = "La edad es obligatoria")
  private Integer edad;

  @NotBlank(message = "La historia es obligatoria")
  private String historia;

  @NotNull(message = "El peso es obligatorio")
  private Float peso;

  @ManyToMany(fetch = LAZY)
  @JsonIgnoreProperties("personajes")
  @JoinTable(name = "personaje_pelicula_serie",
      joinColumns = @JoinColumn(name = "personaje_id"),
      inverseJoinColumns = @JoinColumn(name = "pelicula_serie_id"))
  private Set<PeliculaSerie> peliculasSeries = new HashSet<>();

  public void merge(Personaje personajeActualizado) {
    nombre = personajeActualizado.getNombre();
    peso = personajeActualizado.getPeso();
    imagen = personajeActualizado.getImagen();
    edad = personajeActualizado.getEdad();
    historia = personajeActualizado.getHistoria();
    setPeliculasSeries(personajeActualizado.getPeliculasSeries());
  }
}
