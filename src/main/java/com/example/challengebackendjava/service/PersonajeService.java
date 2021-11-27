package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.PersonajeRepository;
import com.example.challengebackendjava.error.NotFoundException;
import com.example.challengebackendjava.model.Personaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PersonajeService {

  @Autowired
  PersonajeRepository personajeRepository;

  public List<Personaje> all() {
    return personajeRepository.all();
  }

  public Personaje findById(Integer id) {
    var personaje = personajeRepository.findById(id);

    if (personaje == null) {
      throw new NotFoundException("No se encontro el personaje con el id " + id);
    }
    return personaje;
  }

  public void actualizar(Personaje personajeActualizado, Integer id) {
    if (!Objects.equals(personajeActualizado.getId(), id)) {
      throw new NotFoundException("El id del personaje no es correcto");
    }

    var personajeEncontrado = personajeRepository.findById(id);

    if (personajeEncontrado == null) {
      throw new NotFoundException("No se encotro el personaje");
    }

    personajeRepository.update(personajeEncontrado, personajeActualizado);
  }

  public void eliminar(Integer id) {
    var personaje = personajeRepository.findById(id);

    if (personaje == null) {
      throw new NotFoundException("El personaje no existe");
    }

    personajeRepository.eliminar(personaje);
    eliminarPersonajeDePeliculas(personaje);
  }

  public void crear(Personaje personaje) {
    personajeRepository.crear(personaje);
  }

  // TODO: usar personaje.eliminarseDePeliculas()
  private void eliminarPersonajeDePeliculas(Personaje personaje) {
    personaje
            .getPeliculaSerie()
            .forEach(peliculaSerie -> peliculaSerie.eliminarPeronaje(personaje));
  }
}
