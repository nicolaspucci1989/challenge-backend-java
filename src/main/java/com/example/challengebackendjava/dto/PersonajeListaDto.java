package com.example.challengebackendjava.dto;

import com.example.challengebackendjava.model.Personaje;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class PersonajeListaDto {
  private Long id;
  private String nombre;
  private String imagen;

  public static PersonajeListaDto fromPersonaje(Personaje personaje) {
    return new PersonajeListaDto(personaje.getId(), personaje.getNombre(), personaje.getImagen());
  }
}
