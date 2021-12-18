package com.example.challengebackendjava.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaSerie implements Comparable<PeliculaSerie> {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @NotBlank(message = "La imagen es obligatoria")
  private String imagen;

  @NotBlank(message = "El titulo es obligatorio")
  private String titulo;

  @NotNull(message = "La fecha es obligatoria")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate fechaDeCreacion;

  @NotNull(message = "La calificacion es obligatoria")
  private Integer calificacion;

  @JsonIgnoreProperties("peliculasSeries")
  @ManyToMany
  @JoinTable(name = "personaje_pelicula_serie",
      joinColumns = @JoinColumn(name = "pelicula_serie_id"),
      inverseJoinColumns = @JoinColumn(name = "personaje_id"))
  private Set<Personaje> personajes = new HashSet<>();

  public PeliculaSerie(String imagen,
                       String titulo,
                       LocalDate fechaDeCreacion,
                       Integer calificacion) {
    this.imagen = imagen;
    this.titulo = titulo;
    this.fechaDeCreacion = fechaDeCreacion;
    this.calificacion = calificacion;
  }

  public void agregarPersonaje(Personaje personaje) {
    this.personajes.add(personaje);
  }

  public int compareTo(PeliculaSerie otraPeliculaSerie) {
    return getFechaDeCreacion().compareTo(otraPeliculaSerie.getFechaDeCreacion());
  }

  public void merge(PeliculaSerie peliculaSerieActualizada) {
    imagen = peliculaSerieActualizada.getImagen();
    titulo = peliculaSerieActualizada.getTitulo();
    fechaDeCreacion = peliculaSerieActualizada.getFechaDeCreacion();
    calificacion = peliculaSerieActualizada.getCalificacion();
    personajes = peliculaSerieActualizada.getPersonajes();
  }

}
