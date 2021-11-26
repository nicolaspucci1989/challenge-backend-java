package com.example.challengebackendjava.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Genero extends Entidad{
    String nombre;
    String imagen;
    Set<PeliculaSerie> peliculaSeries = new HashSet<>();

  public Genero(String nombre, String imagen) {
    this.nombre = nombre;
    this.imagen = imagen;
  }

  public void agregarPelicula(PeliculaSerie peliculaSerie){
    peliculaSeries.add(peliculaSerie);
  }

  @Override
    public void update(Entidad entidad) {

    }

  public boolean tienePelicula(PeliculaSerie peliculaSerie) {
      return this.peliculaSeries
          .stream().anyMatch(peli -> Objects.equals(peli.getId(), peliculaSerie.getId()));
    }
}
