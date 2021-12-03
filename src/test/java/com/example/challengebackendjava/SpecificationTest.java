package com.example.challengebackendjava;

import com.example.challengebackendjava.dao.*;
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
import java.util.*;

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
  private PeliculaSerie peliDonald1;
  private PeliculaSerie peliLohan;

  @Test
  @DisplayName("podemos filtrar por nombre de personaje")
  public void test() {
    Optional<String> nombre = Optional.of("Donald");
    Specification<Personaje> spec = PersonajeSpecification.nombreIgualA(nombre);

    List<Personaje> resultado = personajeRepository.findAll(spec);

    Assertions.assertTrue(
        resultado
            .stream()
            .anyMatch(personaje -> Objects.equals(personaje.getNombre(), donald.getNombre()))
    );
  }

  @Test
  @DisplayName("podemos filtrar por edad")
  public void filtrarPorEdad() {
    Optional<Integer> edad = Optional.of(33);
    Specification<Personaje> spec = PersonajeSpecification.edadIguala(edad);
    List<Personaje> resultado = personajeRepository.findAll(spec);

    Assertions.assertTrue(
        resultado
            .stream()
            .anyMatch(personaje -> Objects.equals(personaje.getId(), donald.getId()))
    );
  }

  @Test
  @DisplayName("podemos filtrar por las pelis en las que participaron")
  public void filtrarPorPelis() {
    List<Long> idPelis = Arrays.asList(peliDonald.getId(), peliDonald1.getId());
    Specification<Personaje> spec = PersonajeSpecification.participoEnPelicula(idPelis);
    List<Personaje> resultado = personajeRepository.findAll(spec);

    Assertions.assertTrue(
        resultado
            .stream()
            .anyMatch(personaje -> Objects.equals(personaje.getNombre(), donald.getNombre()))
    );

    Assertions.assertFalse(
        resultado
            .stream()
            .anyMatch(personaje -> Objects.equals(personaje.getNombre(), lohan.getNombre()))
    );
  }

  @Test
  @DisplayName("podemos combinar especificaciones")
  public void combinarEspecificaciones() {
    Optional<Integer> edad = Optional.of(donald.getEdad());
    Optional<String> nombre = Optional.of(donald.getNombre());
    List<Long> idPelis = Arrays.asList(peliDonald.getId(), peliDonald1.getId());

    CriterioDeBusquedaPersonaje criterio = CriterioDeBusquedaPersonaje
        .builder()
        .edad(edad)
        .name(nombre)
        .idPelis(idPelis)
        .build();

    Specification<Personaje> spec = PersonajeSpecification.createPersonajeSpecification(criterio);

    List<Personaje> resultado = personajeRepository.findAll(spec);

    Assertions.assertTrue(resultado.stream().anyMatch(personaje -> Objects.equals(personaje.getNombre(), this.donald.getNombre())));
    Assertions.assertFalse(resultado.stream().anyMatch(personaje -> Objects.equals(personaje.getEdad(), donald2.getEdad())));
    Assertions.assertFalse(resultado.stream().anyMatch(personaje -> Objects.equals(personaje.getNombre(), lohan.getNombre())));
  }

  @BeforeEach
  public void init() {
    peliDonald = new PeliculaSerie(
        "/img/peli.donald.jpg",
        "Peli donald",
        LocalDate.now(),
        3
    );

    peliLohan = new PeliculaSerie(
        "/img/peli.donald.jpg",
        "Peli lohan",
        LocalDate.now(),
        3
    );
    peliDonald1 = new PeliculaSerie(
        "/img/peli.donald1.jpg",
        "Peli donald 1",
        LocalDate.now(),
        4
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
    peliculaSerieRepository.save(peliDonald1);
    peliculaSerieRepository.save(peliLohan);
    personajeRepository.save(lohan);

    peliLohan.agregarPersonaje(lohan);
    peliDonald.agregarPersonaje(donald);
    peliDonald1.agregarPersonaje(donald);
    peliLohan = peliculaSerieRepository.save(peliLohan);
    peliDonald = peliculaSerieRepository.save(peliDonald);
    peliDonald1 = peliculaSerieRepository.save(peliDonald1);
  }
}