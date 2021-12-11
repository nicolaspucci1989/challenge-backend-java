package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.GeneroRepository;
import com.example.challengebackendjava.model.Genero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneroService {

  @Autowired
  GeneroRepository generoRepository;

  public void save(Genero genero) {
    generoRepository.save(genero);
  }

  public Genero findById(Long id) {
    return generoRepository.findById(id).orElseThrow();
  }
}
