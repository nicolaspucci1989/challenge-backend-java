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
    return peliculaSerieRepository.all();
  }

  public PeliculaSerie findById(Integer id) {
    var peliculaSerie = peliculaSerieRepository.findById(id);

    if(peliculaSerie == null) {
      throw new NotFoundException("La pelicula o serie no existe");
    }

    return peliculaSerie;
  }

  public void actualizar(PeliculaSerie peliculaSerieActualizada, Integer id) {
    if (!Objects.equals(peliculaSerieActualizada.getId(), id)) {
      throw new NotFoundException("El id de la pelicula o serie no es correcto");
    }

    var peliculaSerieEncontrada = peliculaSerieRepository.findById(id);

    if (peliculaSerieEncontrada == null) {
      throw new NotFoundException("No se encontro la pelicula o serie");
    }

    peliculaSerieRepository.update(peliculaSerieEncontrada, peliculaSerieActualizada);
  }
}
