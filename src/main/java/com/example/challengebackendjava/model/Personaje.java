package com.example.challengebackendjava.model;

import java.time.LocalDate;
import java.util.Set;

public class Personaje {
    String imagen;
    String nombre;
    LocalDate fechaDeNacimiento;
    String historia;
    Set<PeliculaSerie> peliculaSerie;
}
