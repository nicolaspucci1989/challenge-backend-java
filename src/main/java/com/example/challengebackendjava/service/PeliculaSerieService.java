package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.PeliculaSerieRepository;
import com.example.challengebackendjava.model.PeliculaSerie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaSerieService {
  @Autowired
  PeliculaSerieRepository peliculaSerieRepository;

  public List<PeliculaSerie> all() {
    return peliculaSerieRepository.all();
  }
}
