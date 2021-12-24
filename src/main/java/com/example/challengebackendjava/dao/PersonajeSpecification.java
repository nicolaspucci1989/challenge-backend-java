package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.model.PeliculaSerie_;
import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.model.Personaje_;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@AllArgsConstructor
public class PersonajeSpecification implements Specification<Personaje> {
  private CriterioDeBusqueda criterio;

  @Override
  public Predicate toPredicate(Root<Personaje> root,
                               CriteriaQuery<?> query,
                               CriteriaBuilder builder) {

    if (criterio.getClave().equalsIgnoreCase("movies")) {
      // TODO: usar builder?
      return root
          .join(Personaje_.PELICULAS_SERIES)
          .get(PeliculaSerie_.ID)
          .in((List<Long>) criterio.getValor());
    }

    if (criterio.getClave().equalsIgnoreCase("name")) {
      return builder.like(
          root.get(Personaje_.NOMBRE), criterio.getValor().toString()
      );
    }

    if (criterio.getClave().equalsIgnoreCase("age")) {
      return builder.equal(
          root.get(Personaje_.EDAD), criterio.getValor()
      );
    }

    return null;
  }
}