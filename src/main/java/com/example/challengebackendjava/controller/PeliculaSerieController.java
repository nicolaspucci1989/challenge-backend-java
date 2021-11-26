package com.example.challengebackendjava.controller;


import com.example.challengebackendjava.model.Genero;
import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.serializer.View;
import com.example.challengebackendjava.service.GeneroService;
import com.example.challengebackendjava.service.PeliculaSerieService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class PeliculaSerieController {
  @Autowired
  PeliculaSerieService peliculaSerieService;

  @Autowired
  GeneroService generoService;

  @GetMapping("/movies")
  @JsonView(View.PeliculaSerie.Lista.class)
  public ResponseEntity<List<PeliculaSerie>> getPeliculaSeries(@RequestParam Map<String, String> queryParams) {
    String nombre = queryParams.get("name");
    String genre = queryParams.get("genre");
    String order = queryParams.get("order"); // ordenar por fecha de creacion

    List<PeliculaSerie> peliculasSeries = peliculaSerieService.all();

    if (nombre != null) {
      peliculasSeries = peliculasSeries.stream()
          .filter(peliculaSerie -> peliculaSerie.nombreCoincide(nombre))
          .collect(Collectors.toList());
    }

    if (genre != null) {
      Genero genero = generoService.findById(Integer.valueOf(genre));

      peliculasSeries = peliculasSeries.stream()
          .filter(genero::tienePelicula)
          .collect(Collectors.toList());
    }

    return ResponseEntity.ok(peliculasSeries);
  }

  @GetMapping("/movies/{id}")
  @JsonView(View.PeliculaSerie.Detalle.class)
  public ResponseEntity<PeliculaSerie> getPeliculaSerie(@PathVariable Integer id) {
    return ResponseEntity.ok(peliculaSerieService.findById(id));
  }

  @PutMapping("/movies/{id}")
  public ResponseEntity<String> actualizarPeliculaSeire(@RequestBody PeliculaSerie peliculaSerie, @PathVariable Integer id) {
    peliculaSerieService.actualizar(peliculaSerie, id);
    return ResponseEntity.ok().body("Se actualizo la pelicula correctamente");
  }

  @DeleteMapping("/movies/{id}")
  public ResponseEntity<String> eliminarPeliculaSerie(@PathVariable Integer id) {
    peliculaSerieService.eliminar(id);
    return ResponseEntity.ok().body("La pelicula o serie se elimino correctamente");
  }

  @PostMapping("/movies")
  public ResponseEntity<String> crearPeliculaSerie(@RequestBody PeliculaSerie peliculaSerie){
    peliculaSerieService.crear(peliculaSerie);
    return ResponseEntity.ok().body("La pelicula o serie fue creada correctamente");
  }
}
