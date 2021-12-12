package com.example.challengebackendjava.dto;

import com.example.challengebackendjava.model.Personaje;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class PersonajeDto {
  private Long id;
  private String nombre;
  private String imagen;

  public static PersonajeDto fromPersonaje(Personaje personaje) {
    return new PersonajeDto(personaje.getId(), personaje.getNombre(), personaje.getImagen());
  }
}
