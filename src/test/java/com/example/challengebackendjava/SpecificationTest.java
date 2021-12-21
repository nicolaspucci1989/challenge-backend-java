package com.example.challengebackendjava;

import com.example.challengebackendjava.dao.CriterioDeBusqueda;
import com.example.challengebackendjava.dao.PeliculaSerieRepository;
import com.example.challengebackendjava.dao.PersonajeRepository;
import com.example.challengebackendjava.dao.PersonajeSpecification;
import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.model.Personaje;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Dada una especificacion de usuario")
public class SpecificationTest {

  @Autowired
  private PersonajeRepository personajeRepository;

  @Autowired
  private PeliculaSerieRepository peliculaSerieRepository;

  private Personaje donald;
  private Personaje donald2;
  private Personaje lohan;

  private PeliculaSerie peliDonald;

  @Test
  @DisplayName("podemos encontrar el usuario personaje por su nombre")
  public void specTest() {
    PersonajeSpecification spec1 = new PersonajeSpecification(new CriterioDeBusqueda("nombre", "=", "Donald"));
    List<Personaje> resultados = personajeRepository.findAll(spec1);

    Assertions.assertTrue(resultados.stream().anyMatch(personaje -> Objects.equals(personaje.getNombre(), donald.getNombre())));
    Assertions.assertFalse(resultados.stream().anyMatch(personaje -> Objects.equals(personaje.getNombre(), lohan.getNombre())));
  }

  @Test
  @DisplayName("podemos combinar specs")
  public void combinarSpecs() {
    PersonajeSpecification spec1 = new PersonajeSpecification(new CriterioDeBusqueda("nombre", "=", "Donald"));
    PersonajeSpecification spec2 = new PersonajeSpecification(new CriterioDeBusqueda("edad", "=", "33"));

    List<Personaje> resultado = personajeRepository.findAll(Specification.where(spec1).and(spec2));

    Assertions.assertTrue(resultado.stream().anyMatch(personaje -> Objects.equals(personaje.getNombre(), donald.getNombre())));
    Assertions.assertFalse(resultado.stream().anyMatch(personaje -> Objects.equals(personaje.getEdad(), donald2.getEdad())));
    Assertions.assertFalse(resultado.stream().anyMatch(personaje -> Objects.equals(personaje.getNombre(), lohan.getNombre())));
  }

  @Test
  @DisplayName("podemos encontrar un personaje que participo en una pelicula")
  public void estaEnPeli() {
    PersonajeSpecification spec1 = new PersonajeSpecification(new CriterioDeBusqueda("movies", "=", peliDonald.getId().toString()));

    List<Personaje> resultado = personajeRepository.findAll(spec1);

    Assertions.assertTrue(
        resultado
            .stream()
            .anyMatch(personaje -> Objects.equals(personaje.getNombre(), donald.getNombre()))
    );
  }

  @BeforeEach
  public void init() {
    peliDonald = new PeliculaSerie(
        "/img/peli.donald.jpg",
        "Peli donald",
        LocalDate.now(),
        3
    );
    donald = new Personaje(null,
        "/img/donald.jpg",
        "Donald",
        33,
        "historia",
        90f,
        new HashSet<>());

    donald2 = new Personaje(null,
        "/img/donald.jpg",
        "Donald",
        34,
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
    peliculaSerieRepository.save(peliDonald);
    personajeRepository.save(lohan);

    peliDonald.agregarPersonaje(donald);
    peliDonald = peliculaSerieRepository.save(peliDonald);
  }
}
