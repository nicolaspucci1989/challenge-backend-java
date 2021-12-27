package com.example.challengebackendjava;

import com.example.challengebackendjava.dao.PeliculaSerieRepository;
import com.example.challengebackendjava.dao.filter.PeliculaSerieSpecification;
import com.example.challengebackendjava.model.PeliculaSerie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Dada una especificacion de peliculas")
public class PeliculaSpecificationTest {

  @Autowired
  private PeliculaSerieRepository peliculaSerieRepository;

  @Test
  public void filtrarPorNombre() {
    String titulo = "Enredados";
    Optional<String> nombre = Optional.of(titulo);
    Specification<PeliculaSerie> spec = PeliculaSerieSpecification.nombreIgualA(nombre);
    List<PeliculaSerie> resultados = peliculaSerieRepository.findAll(spec);

    Assertions.assertTrue(
        resultados
            .stream()
            .anyMatch(peliculaSerie -> Objects.equals(peliculaSerie.getTitulo(), titulo))
    );
    Assertions.assertEquals(1, resultados.size());
  }

//  @Test
//  public void filtrarPorGenero() {
//    peliculaSerieRepository.findAll(PeliculaSerieSpecification.generoIgualA(Optional.of(1L)));
//  }
}
