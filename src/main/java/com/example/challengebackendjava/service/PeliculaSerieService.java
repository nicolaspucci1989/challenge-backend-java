package com.example.challengebackendjava.service;

import com.example.challengebackendjava.controller.OrderEnum;
import com.example.challengebackendjava.dao.PeliculaSerieRepository;
import com.example.challengebackendjava.dao.filter.PeliculaSerieSpecification;
import com.example.challengebackendjava.error.NotFoundException;
import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.model.PeliculaSerie_;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.TypeCache;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

  public List<PeliculaSerie> all(CriterioDeBusquedaPelicula criterio) {
    final Specification<PeliculaSerie> spec = PeliculaSerieSpecification.createPeliculaSerieSpecification(criterio);
    return criterio.getOrder()
        .map(orderEnum -> {
          if(orderEnum == OrderEnum.ASC) {
            return peliculaSerieRepository.findAll(spec, Sort.by(PeliculaSerie_.FECHA_DE_CREACION).ascending());
          }
          return peliculaSerieRepository.findAll(spec, Sort.by(PeliculaSerie_.FECHA_DE_CREACION).descending());
        })
        .orElseGet(() -> peliculaSerieRepository.findAll(spec));
  }
}
