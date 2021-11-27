package com.example.challengebackendjava.model;

import com.example.challengebackendjava.serializer.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.example.challengebackendjava.model.Helper.stringsCoinciden;

public class PeliculaSerie extends Entidad implements Comparable<PeliculaSerie> {
  String imagen;
  String titulo;
  LocalDate fehcaDeCreacion;
  Integer calificacion;
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

  @JsonView({
          View.PeliculaSerie.Lista.class,
          View.PeliculaSerie.Detalle.class,
          View.Personaje.Detalle.class})
  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  @JsonView({View.PeliculaSerie.Lista.class, View.PeliculaSerie.Detalle.class})
  public LocalDate getFehcaDeCreacion() {
    return fehcaDeCreacion;
  }

  public void setFehcaDeCreacion(LocalDate fehcaDeCreacion) {
    this.fehcaDeCreacion = fehcaDeCreacion;
  }

  @JsonView(View.PeliculaSerie.Detalle.class)
  public Integer getCalificacion() {
    return calificacion;
  }

  public void setCalificacion(Integer calificacion) {
    this.calificacion = calificacion;
  }

  @JsonView(View.PeliculaSerie.Detalle.class)
  public Set<Personaje> getPersonajes() {
    return personajes;
  }

  public void setPersonajes(Set<Personaje> personajes) {
    this.personajes = personajes;
  }

  @Override
  public void update(Entidad entidad) {
    var peliculaSerie = (PeliculaSerie) entidad;

    this.setTitulo(peliculaSerie.getTitulo());
    this.setCalificacion(peliculaSerie.getCalificacion());
    this.setImagen(peliculaSerie.getImagen());
    this.setFehcaDeCreacion(peliculaSerie.getFehcaDeCreacion());
    // TODO: validar que sean personajes existentes
    // this.setPersonajes(peliculaSerie.getPersonajes());
  }

  public void eliminarPeronaje(Personaje personaje) {
    personajes.remove(personaje);
  }

  public boolean nombreCoincide(String nombre) {
    return stringsCoinciden(getTitulo(), nombre);
  }

  @Override
  public int compareTo(PeliculaSerie otraPeliculaSerie) {
    return getFehcaDeCreacion().compareTo(otraPeliculaSerie.getFehcaDeCreacion());
  }
}
