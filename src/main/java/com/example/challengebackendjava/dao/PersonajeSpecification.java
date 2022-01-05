package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.model.PeliculaSerie_;
import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.model.Personaje_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonajeSpecification {
  public static Specification<Personaje> createPersonajeSpecification(CriterioDeBusquedaPersonaje criterio) {
    return nombreIgualA(criterio.getName())
        .and(edadIguala(criterio.getEdad()))
        .and(pesoIgualA(criterio.getPeso()))
        .and(participoEnPelicula(criterio.getIdPelis()));
  }

  public static Specification<Personaje> pesoIgualA(Optional<Float> peso) {
    return (root, query, builder) ->
        peso
            .map(aFloat -> builder.equal(root.get(Personaje_.PESO), aFloat))
            .orElse(null);
  }

  public static Specification<Personaje> nombreIgualA(Optional<String> nombre) {
    return ((root, query, builder) ->
        nombre
            .map(s -> builder.like(root.get(Personaje_.NOMBRE), s))
            .orElse(null));
  }

  public static Specification<Personaje> edadIguala(Optional<Integer> edad) {
    return ((root, query, builder) ->
        edad
            .map(aInt -> builder.equal(root.get(Personaje_.EDAD), aInt))
            .orElse(null));
  }

  public static Specification<Personaje> participoEnPelicula(List<Long> idPelis) {
    if (CollectionUtils.isEmpty(idPelis)) {
      return null;
    }

    return ((root, query, criteriaBuilder) ->
        root
            .join(Personaje_.PELICULAS_SERIES)
            .get(PeliculaSerie_.ID)
            .in(idPelis)
    );
  }
}
