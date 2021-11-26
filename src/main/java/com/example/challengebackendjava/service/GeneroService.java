package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.GeneroRepository;
import com.example.challengebackendjava.error.NotFoundException;
import com.example.challengebackendjava.model.Genero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneroService {

  @Autowired
  GeneroRepository generoRepository;

  public Genero findById(Integer id) {
    Genero genero = generoRepository.findById(id);

    if (genero == null) {
      throw new NotFoundException("El genero no existe");
    }

    return genero;
  }
}
