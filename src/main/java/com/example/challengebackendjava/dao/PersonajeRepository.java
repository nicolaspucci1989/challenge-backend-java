package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonajeRepository extends JpaRepository<Personaje, Long> {
  Personaje findByNombre(String nombre);
}
