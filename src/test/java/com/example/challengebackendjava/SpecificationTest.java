package com.example.challengebackendjava;

import com.example.challengebackendjava.dao.CriterioDeBusqueda;
import com.example.challengebackendjava.dao.PersonajeRepository;
import com.example.challengebackendjava.dao.PersonajeSpecification;
import com.example.challengebackendjava.model.Personaje;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@ActiveProfiles("test")
public class SpecificationTest {

  @Autowired
  private PersonajeRepository personajeRepository;

  private Personaje donald;
  private Personaje lohan;

  @Test
  public void specTest() {
    PersonajeSpecification spec1 = new PersonajeSpecification(new CriterioDeBusqueda("nombre", "=", "Donald"));
    List<Personaje> resultados = personajeRepository.findAll(spec1);

    Assertions.assertTrue(resultados.stream().anyMatch(personaje -> Objects.equals(personaje.getNombre(), donald.getNombre())));
    Assertions.assertFalse(resultados.stream().anyMatch(personaje -> Objects.equals(personaje.getNombre(), lohan.getNombre())));
  }

  @BeforeEach
  public void init() {
    donald = new Personaje(null,
        "/img/donald.jpg",
        "Donald",
        33,
        "historia",
        90f,
        new HashSet<>());

    lohan = new Personaje(null,
        "/img/lohan.jpg",
        "Lohan",
        33,
        "historia",
        90f,
        new HashSet<>());

    personajeRepository.save(donald);
    personajeRepository.save(lohan);
  }
}
