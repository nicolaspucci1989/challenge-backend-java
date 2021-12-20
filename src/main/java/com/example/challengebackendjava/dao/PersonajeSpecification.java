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

@AllArgsConstructor
public class PersonajeSpecification implements Specification<Personaje> {
  private CriterioDeBusqueda criterio;

  @Override
  public Predicate toPredicate(Root<Personaje> root,
                               CriteriaQuery<?> query,
                               CriteriaBuilder builder) {

    if (criterio.getClave().equalsIgnoreCase("movies")) {
      return root
          .join(Personaje_.PELICULAS_SERIES)
          .get(PeliculaSerie_.ID)
          .in(criterio.getValor());
    }

    if (criterio.getOperacion().equalsIgnoreCase("=")) {
      if (root.get(criterio.getClave()).getJavaType() == String.class) {
        return builder.like(
            root.get(criterio.getClave()), "%" + criterio.getValor() + "%");
      } else {
        return builder.equal(root.get(criterio.getClave()), criterio.getValor());
      }
    }

    return null;
  }
}