package com.example.challengebackendjava.model;

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
import java.util.stream.Collectors;

import static com.example.challengebackendjava.model.Helper.stringsCoinciden;
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
  private String imagen;
  private String nombre;
  private Integer edad;
  private String historia;
  private Float peso;
  @ManyToMany(mappedBy = "personajes")
  private Set<PeliculaSerie> peliculasSeries = new HashSet<>();

  public boolean nombreCoincide(String nombre) {
    return stringsCoinciden(getNombre(), nombre);
  }

  public boolean edadCoincide(Integer edad) {
    return Objects.equals(getEdad(), edad);
  }

  public void agregarPelicualaSerie(PeliculaSerie peliculaSerie) {
    peliculasSeries.add(peliculaSerie);
  }

  public boolean estuvoEnAlgunaPelicula(Set<Long> peliculas) {
    return peliculas.stream().anyMatch(this::estuvoEnPelicula);
  }

  private boolean estuvoEnPelicula(Long idPelicula) {
    return this.idPeliculas().contains(idPelicula);
  }

  private Set<Long> idPeliculas() {
    return peliculasSeries.stream().map(PeliculaSerie::getId).collect(Collectors.toSet());
  }

  //  Set<PeliculaSerie> peliculasSeries = new HashSet<>();
}
