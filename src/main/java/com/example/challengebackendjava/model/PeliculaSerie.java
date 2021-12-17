package com.example.challengebackendjava.model;

import com.example.challengebackendjava.serializer.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static com.example.challengebackendjava.model.Helper.stringsCoinciden;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaSerie implements Comparable<PeliculaSerie> {
  @Id @GeneratedValue(strategy = AUTO)
  private Long id;

  @NotBlank(message = "La imagen es obligatoria")
  private String imagen;

  @NotBlank(message = "El titulo es obligatorio")
  private String titulo;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate fehcaDeCreacion;

  private Integer calificacion;

  @JsonIgnoreProperties("peliculasSeries")
  @ManyToMany
  @JoinTable(name = "personaje_pelicula_serie",
  joinColumns = @JoinColumn(name = "pelicula_serie_id"),
  inverseJoinColumns = @JoinColumn(name = "personaje_id"))
  private Set<Personaje> personajes = new HashSet<>();

  public PeliculaSerie(String imagen,
                       String titulo,
                       LocalDate fehcaDeCreacion,
                       Integer calificacion) {
    this.imagen = imagen;
    this.titulo = titulo;
    this.fehcaDeCreacion = fehcaDeCreacion;
    this.calificacion = calificacion;
  }

  public void agregarPersonaje(Personaje personaje) {
    this.personajes.add(personaje);
  }

  @JsonView({
          View.PeliculaSerie.Lista.class,
          View.PeliculaSerie.Detalle.class,
  })
  public String getImagen() {
    return imagen;
  }

  public void setImagen(String imagen) {
    this.imagen = imagen;
  }

  public boolean nombreCoincide(String nombre) {
    return stringsCoinciden(titulo, nombre);
  }

  public int compareTo(PeliculaSerie otraPeliculaSerie) {
    return getFehcaDeCreacion().compareTo(otraPeliculaSerie.getFehcaDeCreacion());
  }

  public void merge(PeliculaSerie peliculaSerieActualizada) {
    imagen = peliculaSerieActualizada.getImagen();
    titulo = peliculaSerieActualizada.getTitulo();
    fehcaDeCreacion = peliculaSerieActualizada.getFehcaDeCreacion();
    calificacion = peliculaSerieActualizada.getCalificacion();
    personajes = peliculaSerieActualizada.getPersonajes();
  }

}
