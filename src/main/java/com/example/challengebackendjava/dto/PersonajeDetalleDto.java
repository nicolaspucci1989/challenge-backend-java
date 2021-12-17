package com.example.challengebackendjava.dto;

import com.example.challengebackendjava.model.Personaje;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class PersonajeDetalleDto {
  private Long id;
  private String nombre;
  private String imagen;
  private Float peso;
  private Integer edad;
  private String historia;
  private Set<PeliculaSerieListDto> peliculasSeries;

  public static PersonajeDetalleDto fromPersonaje(Personaje personaje) {
    return new PersonajeDetalleDto(personaje.getId(),
        personaje.getNombre(),
        personaje.getImagen(),
        personaje.getPeso(),
        personaje.getEdad(),
        personaje.getHistoria(),
        getPeliculasSeriesDto(personaje)
        );
  }

  private static Set<PeliculaSerieListDto> getPeliculasSeriesDto(Personaje personaje) {
    return personaje.getPeliculasSeries()
        .stream().map(PeliculaSerieListDto::fromPeliculaSerie)
        .collect(Collectors.toSet());
  }
}
