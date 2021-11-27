package com.example.challengebackendjava.model;

import com.example.challengebackendjava.serializer.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.challengebackendjava.model.Helper.stringsCoinciden;

public class Personaje extends Entidad {
  String imagen;
  String nombre;
  Integer edad;
  String historia;
  // TODO: renombrar
  Set<PeliculaSerie> peliculasSeries = new HashSet<>();
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
    this.peliculasSeries.add(peliculaSerie);
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
  public Set<PeliculaSerie> getPeliculasSeries() {
    return peliculasSeries;
  }


  public void setPeliculasSeries(Set<PeliculaSerie> peliculasSeries) {
    this.peliculasSeries = peliculasSeries;
  }

  @Override
  public void update(Entidad entidad) {
    var personaje = (Personaje) entidad;

    this.setNombre(personaje.getNombre());
    this.setEdad(personaje.getEdad());
    this.setHistoria(personaje.getHistoria());
    this.setImagen(personaje.getImagen());
    this.setPeliculasSeries(personaje.getPeliculasSeries());
  }

  @Override
  public boolean esValido() {
    return tieneNombreValido() && tieneImagenValida() && tieneEdadValida();
  }

  private boolean tieneEdadValida() {
    return edad > 0;
  }

  private boolean tieneImagenValida() {
    return getImagen().length() > 1;
  }

  private boolean tieneNombreValido() {
    return getNombre().length() > 1;
  }

  public void eliminarPeliculaSerie(PeliculaSerie peliculaSerie) {
    this.peliculasSeries.remove(peliculaSerie);
  }

  public boolean estuvoEnAlgunaPelicula(Set<Integer> peliculas) {
    return peliculas.stream().anyMatch(this::estuvoEnPelicula);
  }

  private boolean estuvoEnPelicula(Integer idPelicula) {
    return this.idPeliculas().contains(idPelicula);
  }

  private Set<Integer> idPeliculas() {
    return peliculasSeries.stream().map(Entidad::getId).collect(Collectors.toSet());
  }

  public boolean nombreCoincide(String nombre) {
    return stringsCoinciden(getNombre(), nombre);
  }

  public boolean edadCoincide(Integer edad) {
    return Objects.equals(getEdad(), edad);
  }
}
