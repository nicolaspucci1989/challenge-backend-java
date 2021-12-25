package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.model.Personaje;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PersonajeRepository extends JpaRepository<Personaje, Long>, JpaSpecificationExecutor<Personaje> {
  Personaje findByNombre(String nombre);

  @EntityGraph(attributePaths = "peliculasSeries")
  @Override
  Optional<Personaje> findById(Long aLong);
}
