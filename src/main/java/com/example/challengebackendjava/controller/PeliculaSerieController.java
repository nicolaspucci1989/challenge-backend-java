package com.example.challengebackendjava.controller;


import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.service.PeliculaSerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PeliculaSerieController {
  @Autowired
  PeliculaSerieService peliculaSerieService;

  @GetMapping("/movies")
  public ResponseEntity<List<PeliculaSerie>> getPeliculaSeries() {
    return ResponseEntity.ok(peliculaSerieService.all());
  }
}
