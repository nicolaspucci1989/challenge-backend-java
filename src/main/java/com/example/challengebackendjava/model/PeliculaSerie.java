package com.example.challengebackendjava.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PeliculaSerie extends Entidad{
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFehcaDeCreacion() {
        return fehcaDeCreacion;
    }

    public void setFehcaDeCreacion(LocalDate fehcaDeCreacion) {
        this.fehcaDeCreacion = fehcaDeCreacion;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }
}
