package com.example.challengebackendjava.dto;

import com.example.challengebackendjava.model.PeliculaSerie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class PeliculaSerieDetalleDto {
  private Long id;
  private String imagen;
  private String titulo;
  private LocalDate fechaDeCreacion;
  private Integer calificacion;
  private Set<PersonajeListaDto> personajes;

  public static PeliculaSerieDetalleDto fromPeliculaSerie(PeliculaSerie peliculaSerie) {
    return new PeliculaSerieDetalleDto(
        peliculaSerie.getId(),
        peliculaSerie.getImagen(),
        peliculaSerie.getTitulo(),
        peliculaSerie.getFehcaDeCreacion(),
        peliculaSerie.getCalificacion(),
        getPersonajesDto(peliculaSerie)
    );
  }

  private static Set<PersonajeListaDto> getPersonajesDto(PeliculaSerie peliculaSerie) {
    return peliculaSerie.getPersonajes()
        .stream().map(PersonajeListaDto::fromPersonaje)
        .collect(Collectors.toSet());
  }
}
