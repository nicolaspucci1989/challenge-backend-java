package com.example.challengebackendjava.model;

import java.util.HashSet;
import java.util.Set;

public class Personaje {
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
}
