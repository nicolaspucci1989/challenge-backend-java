package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.PersonajeRepository;
import com.example.challengebackendjava.error.NotFoundException;
import com.example.challengebackendjava.model.Personaje;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PersonajeService {
  private final PersonajeRepository personajeRepository;

  public Personaje save(Personaje personaje) {
//    personaje.validar();
    return personajeRepository.save(personaje);
  }

  public List<Personaje> all() {
    return personajeRepository.findAll();
  }

  public Personaje findById(Long id) {
    return personajeRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("No se encontro el personaje"));
  }

  public void actualizar(Personaje personajeActualizado, Long id) {
    if (!Objects.equals(personajeActualizado.getId(), id)) {
      throw new NotFoundException("El id del personaje no es correcto");
    }

    Personaje personajeEncontrado = findById(id);
    personajeEncontrado.merge(personajeActualizado);
    personajeRepository.save(personajeEncontrado);
  }

  public void eliminar(Long id) {
    var personaje = findById(id);

    personajeRepository.delete(personaje);
//    eliminarPersonajeDePeliculas(personaje);
  }

  public List<Personaje> all(Specification<Personaje> spec) {
    return personajeRepository.findAll(spec);
  }

//  private void eliminarPersonajeDePeliculas(Personaje personaje) {
//    personaje.getPeliculasSeries()
//            .forEach(peliculaSerie ->
//                peliculaSerie.eliminarPeronaje(personaje));
//  }
}
