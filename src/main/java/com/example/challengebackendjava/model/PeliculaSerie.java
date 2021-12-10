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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.example.challengebackendjava.model.Helper.stringsCoinciden;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaSerie implements Comparable<PeliculaSerie> {
  @Id @GeneratedValue(strategy = AUTO)
  @JsonView(View.PeliculaSerie.Lista.class)
  private Long id;
  @JsonView(View.PeliculaSerie.Lista.class)
  String imagen;
  @JsonView(View.PeliculaSerie.Lista.class)
  String titulo;
  LocalDate fehcaDeCreacion;
  Integer calificacion;
  @ManyToMany
  Set<Personaje> personajes = new HashSet<>();

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
}
