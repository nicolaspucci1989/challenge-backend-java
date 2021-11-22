package com.example.challengebackendjava.model;

import com.example.challengebackendjava.serializer.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.HashSet;
import java.util.Set;

public class Personaje extends Entidad{
    String imagen;
    String nombre;
    Integer edad;
    String historia;
    Set<PeliculaSerie> peliculaSerie = new HashSet<>();
    Float peso; // TODO: agregar peso a constructor

    public Personaje(String imagen,
                     String nombre,
                     Integer edad,
                     String historia) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.edad = edad;
        this.historia = historia;
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

    @JsonView({View.Personaje.Detalle.class, View.PeliculaSerie.Detalle.class})
    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    @JsonView({View.Personaje.Detalle.class, View.PeliculaSerie.Detalle.class})
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
//        this.setPeliculaSerie(personaje.getPeliculaSerie());
    }
}
