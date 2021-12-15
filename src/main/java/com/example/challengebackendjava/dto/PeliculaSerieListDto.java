package com.example.challengebackendjava.dto;

import com.example.challengebackendjava.model.PeliculaSerie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PeliculaSerieListDto {
  private Long id;
  private String titulo;
  private String imagen;
  private LocalDate fechaDeCreacion;

  public static PeliculaSerieListDto fromPeliculaSerie(PeliculaSerie peliculaSerie) {
    return new PeliculaSerieListDto(
        peliculaSerie.getId(),
        peliculaSerie.getTitulo(),
        peliculaSerie.getImagen(),
        peliculaSerie.getFehcaDeCreacion()
    );
  }
}