package com.example.challengebackendjava.dao.filter;

import com.example.challengebackendjava.model.Genero;
import com.example.challengebackendjava.model.Genero_;
import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.model.PeliculaSerie_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PeliculaSerieSpecification {
  public static Specification<PeliculaSerie> nombreIgualA(Optional<String> nombre) {
    return (root, query, builder) ->
        nombre
            .map(s -> builder.like(root.get(PeliculaSerie_.TITULO), s))
            .orElse(null);
  }

//  public static Specification<PeliculaSerie> generoIgualA(Optional<Long> idGenero) {
//    return (Root<PeliculaSerie> peliSerie, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
//      Root<Genero> genero = query.from(Genero.class);
////      Predicate equal = builder.equal(genero.get(Genero_.ID), 19);
//      Join<Genero, PeliculaSerie> generoJoin = genero.join(Genero_.PELICULAS_SERIES, JoinType.INNER);
//      genero.get(Genero_.ID).alias("genero_id");
//      return builder.in(generoJoin.in(19));
//    };
//  }
}
