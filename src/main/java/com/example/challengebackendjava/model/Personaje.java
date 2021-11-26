package com.example.challengebackendjava.model;

import com.example.challengebackendjava.serializer.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Personaje extends Entidad {
  String imagen;
  String nombre;
  Integer edad;
  String historia;
  // TODO: renombrar
  Set<PeliculaSerie> peliculaSerie = new HashSet<>();
  Float peso;

  public Personaje(String imagen,
                   String nombre,
                   Integer edad,
                   String historia,
                   Float peso) {
    this.imagen = imagen;
    this.nombre = nombre;
    this.edad = edad;
    this.historia = historia;
    this.peso = peso;
  }

  public void agregarPelicualaSerie(PeliculaSerie peliculaSerie) {
    this.peliculaSerie.add(peliculaSerie);
  }

  @JsonView({View.Personaje.Lista.class, View.Personaje.Detalle.class})
  public String getImagen() {
    return imagen;
  }

  public void setImagen(String imagen) {
    this.imagen = imagen;
  }

  @JsonView({
          View.Personaje.Lista.class,
          View.Personaje.Detalle.class,
          View.PeliculaSerie.Detalle.class
  })
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  @JsonView({View.Personaje.Detalle.class})
  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    this.edad = edad;
  }

  @JsonView(View.Personaje.Detalle.class)
  public Float getPeso() {
    return peso;
  }

  public void setPeso(Float peso) {
    this.peso = peso;
  }

  @JsonView({View.Personaje.Detalle.class})
  public String getHistoria() {
    return historia;
  }

  public void setHistoria(String historia) {
    this.historia = historia;
  }

  @JsonView({View.Personaje.Detalle.class})
  public Set<PeliculaSerie> getPeliculaSerie() {
    return peliculaSerie;
  }


  public void setPeliculaSerie(Set<PeliculaSerie> peliculaSerie) {
    this.peliculaSerie = peliculaSerie;
  }

  @Override
  public void update(Entidad entidad) {
    var personaje = (Personaje) entidad;

    this.setNombre(personaje.getNombre());
    this.setEdad(personaje.getEdad());
    this.setHistoria(personaje.getHistoria());
    this.setImagen(personaje.getImagen());
    // TODO: agregar peliculas
//    this.setPeliculaSerie(personaje.getPeliculaSerie());
    personaje
            .getPeliculaSerie()
            .forEach(this::agregarPelicualaSerie);
  }

  public void eliminarPeliculaSerie(PeliculaSerie peliculaSerie) {
    this.peliculaSerie.remove(peliculaSerie);
  }

  public boolean estuvoEnAlgunaPelicula(Set<Integer> peliculas) {
    return peliculas.stream().anyMatch(this::estuvoEnPelicula);
  }

  private boolean estuvoEnPelicula(Integer idPelicula) {
    return this.idPeliculas().contains(idPelicula);
  }

  private Set<Integer> idPeliculas() {
    return peliculaSerie.stream().map(Entidad::getId).collect(Collectors.toSet());
  }

  public boolean nombreCoincide(String nombre) {
    return getNombre().toLowerCase(Locale.ROOT)
            .contains(nombre.toLowerCase(Locale.ROOT));
  }

  public boolean edadCoincide(Integer edad) {
    return Objects.equals(getEdad(), edad);
  }
}
