package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.PeliculaSerieRepository;
import com.example.challengebackendjava.error.NotFoundException;
import com.example.challengebackendjava.model.PeliculaSerie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PeliculaSerieService {
  private final PeliculaSerieRepository peliculaSerieRepository;

  public List<PeliculaSerie> all() {
    return peliculaSerieRepository.findAll();
  }

  public PeliculaSerie findById(Long id) {
    return peliculaSerieRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("No se encontro la pelicua o serie"));
  }

  public PeliculaSerie actualizar(PeliculaSerie peliculaSerieActualizada, Long id) {
    if (!Objects.equals(peliculaSerieActualizada.getId(), id)) {
      throw new NotFoundException("El id de la pelicula o serie no es correcto");
    }

    PeliculaSerie peliculaSerie = findById(id);
    peliculaSerie.merge(peliculaSerieActualizada);
    return peliculaSerieRepository.save(peliculaSerie);
  }

  public void eliminar(Long id) {
    peliculaSerieRepository.deleteById(id);
  }

  public void save(PeliculaSerie peliculaSerie) {
    peliculaSerieRepository.save(peliculaSerie);
  }

}
