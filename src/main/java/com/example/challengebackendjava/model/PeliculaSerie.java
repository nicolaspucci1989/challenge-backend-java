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
}
