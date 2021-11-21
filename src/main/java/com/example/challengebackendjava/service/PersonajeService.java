package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.PersonajeRepository;
import com.example.challengebackendjava.error.NotFoundException;
import com.example.challengebackendjava.model.Personaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonajeService {

  @Autowired
  PersonajeRepository personajeRepository;

  public List<Personaje> all() {
    return personajeRepository.all();
  }

  public Personaje getById(Integer id) {
    var personaje = personajeRepository.getById(id);

    if (personaje == null) {
      throw new NotFoundException("No se encontro el personaje con el id " + id);
    }
    return personaje;
  }
}
