package com.example.challengebackendjava.dto;

import com.example.challengebackendjava.model.PeliculaSerie;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PeliculaSerieDto {
  private Long id;
  private String imagen;
  private String titulo;

  public static PeliculaSerieDto fromPeliculaSerie(PeliculaSerie peliculaSerie) {
    return new PeliculaSerieDto(
        peliculaSerie.getId(),
        peliculaSerie.getImagen(),
        peliculaSerie.getTitulo()
    );
  }
}
