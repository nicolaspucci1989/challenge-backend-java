package com.example.challengebackendjava.model;

import java.util.HashSet;
import java.util.Set;

public class Personaje extends Entidad{
    String imagen;
    String nombre;
    Integer edad;
    String historia;
    Set<PeliculaSerie> peliculaSerie = new HashSet<>();

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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }
}
