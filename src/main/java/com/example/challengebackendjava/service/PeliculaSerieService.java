package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.PeliculaSerieRepository;
import com.example.challengebackendjava.error.NotFoundException;
import com.example.challengebackendjava.model.PeliculaSerie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PeliculaSerieService {
  @Autowired
  PeliculaSerieRepository peliculaSerieRepository;

  public List<PeliculaSerie> all() {
    return peliculaSerieRepository.findAll();
  }

  public PeliculaSerie findById(Long id) {
    return peliculaSerieRepository.findById(id).orElseThrow();
  }

  public void actualizar(PeliculaSerie peliculaSerieActualizada, Long id) {
    if (!Objects.equals(peliculaSerieActualizada.getId(), id)) {
      throw new NotFoundException("El id de la pelicula o serie no es correcto");
    }

    peliculaSerieRepository.save(peliculaSerieActualizada);
  }

  public void eliminar(Long id) {
    peliculaSerieRepository.deleteById(id);
//    eliminarPeliculaDePersonajes(peliculaSerie);
  }

  public void save(PeliculaSerie peliculaSerie) {
    peliculaSerieRepository.save(peliculaSerie);
  }

//  private void eliminarPeliculaDePersonajes(PeliculaSerie peliculaSerie) {
//    peliculaSerie
//            .getPersonajes()
//            .forEach(personaje -> personaje.eliminarPeliculaSerie(peliculaSerie));
//  }
}
