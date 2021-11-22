package com.example.challengebackendjava.controller;


import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.serializer.View;
import com.example.challengebackendjava.service.PeliculaSerieService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PeliculaSerieController {
  @Autowired
  PeliculaSerieService peliculaSerieService;

  @GetMapping("/movies")
  @JsonView(View.PeliculaSerie.Lista.class)
  public ResponseEntity<List<PeliculaSerie>> getPeliculaSeries() {
    return ResponseEntity.ok(peliculaSerieService.all());
  }

  @GetMapping("/movies/{id}")
  @JsonView(View.PeliculaSerie.Detalle.class)
  public ResponseEntity<PeliculaSerie> getPeliculaSerie(@PathVariable Integer id) {
    return ResponseEntity.ok(peliculaSerieService.findById(id));
  }
}
