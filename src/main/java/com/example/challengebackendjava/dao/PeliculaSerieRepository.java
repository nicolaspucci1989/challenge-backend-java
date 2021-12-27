package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.model.PeliculaSerie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PeliculaSerieRepository extends JpaRepository<PeliculaSerie, Long>, JpaSpecificationExecutor<PeliculaSerie> {

  @EntityGraph(attributePaths = "personajes")
  @Override
  Optional<PeliculaSerie> findById(Long aLong);
}
