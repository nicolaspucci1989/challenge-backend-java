package com.example.challengebackendjava.model;

import java.time.LocalDate;
import java.util.Set;

public class PeliculaSerie {
    String imagen;
    String titulo;
    LocalDate fehcaDeCreacion;
    Integer calificacion;
    Set<Personaje> personajes;
}
