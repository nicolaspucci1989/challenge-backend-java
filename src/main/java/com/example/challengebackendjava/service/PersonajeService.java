package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.PersonajeRepository;
import com.example.challengebackendjava.error.NotFoundException;
import com.example.challengebackendjava.model.Personaje;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class PersonajeService {
  private final PersonajeRepository personajeRepository;

  public Personaje save(Personaje personaje) {
    return personajeRepository.save(personaje);
  }

  public List<Personaje> all() {
    return personajeRepository.findAll();
  }

  public Personaje findById(Long id) {
    return personajeRepository
        .findById(id)
        .orElseThrow();
  }

  public void actualizar(Personaje personajeActualizado, Long id) {
    if (!Objects.equals(personajeActualizado.getId(), id)) {
      throw new NotFoundException("El id del personaje no es correcto");
    }

    personajeRepository.save(personajeActualizado);
  }

  public void eliminar(Long id) {
    var personaje = personajeRepository.findById(id).orElseThrow();

    personajeRepository.delete(personaje);
//    eliminarPersonajeDePeliculas(personaje);
  }

//  private void eliminarPersonajeDePeliculas(Personaje personaje) {
//    personaje.getPeliculasSeries()
//            .forEach(peliculaSerie ->
//                peliculaSerie.eliminarPeronaje(personaje));
//  }
}
