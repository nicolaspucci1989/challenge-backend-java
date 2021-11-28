package com.example.challengebackendjava.controller;


import com.example.challengebackendjava.model.Genero;
import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.serializer.View;
import com.example.challengebackendjava.service.GeneroService;
import com.example.challengebackendjava.service.PeliculaSerieService;
import com.example.challengebackendjava.service.PersonajeService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class PeliculaSerieController {
  @Autowired
  PeliculaSerieService peliculaSerieService;
  @Autowired
  PersonajeService personajeService;
  @Autowired
  GeneroService generoService;

  @GetMapping("/movies")
  @JsonView(View.PeliculaSerie.Lista.class)
  @ApiOperation("Devuelve un listado de todas las peliculas-series con su id, nombre e imagen")
  public ResponseEntity<List<PeliculaSerie>> getPeliculaSeries(
      @RequestParam(required = false) String nombre,
      @RequestParam(required = false) Integer genre,
      @RequestParam(required = false) OrderEnum order
  ) {
    List<PeliculaSerie> peliculasSeries = new ArrayList<>(peliculaSerieService.all());

    if (nombre != null) {
      peliculasSeries = peliculasSeries.stream()
          .filter(peliculaSerie -> peliculaSerie.nombreCoincide(nombre))
          .collect(Collectors.toList());
    }

    if (genre != null) {
      Genero genero = generoService.findById(genre);

      peliculasSeries = peliculasSeries.stream()
          .filter(genero::tienePelicula)
          .collect(Collectors.toList());
    }

    if (order != null) {
      Collections.sort(peliculasSeries);

      if (order.equals(OrderEnum.DESC)) {
        Collections.reverse(peliculasSeries);
      }
    }

    return ResponseEntity.ok(peliculasSeries);
  }

  @GetMapping("/movies/{id}")
  @JsonView(View.PeliculaSerie.Detalle.class)
  @ApiOperation("Permite buscar una pelicula-serie por id, con toda su informacion")
  public ResponseEntity<PeliculaSerie> getPeliculaSerie(@PathVariable Integer id) {
    return ResponseEntity.ok(peliculaSerieService.findById(id));
  }

  @PutMapping("/movies/{id}")
  @ApiOperation("Permite actualizar una pelicula-serie")
  public ResponseEntity<String> actualizarPeliculaSeire(@RequestBody PeliculaSerie peliculaSerie, @PathVariable Integer id) {
    peliculaSerie.setPersonajes(getPersonajesDelRepositorio(peliculaSerie));
    peliculaSerieService.actualizar(peliculaSerie, id);
    return ResponseEntity.ok().body("Se actualizo la pelicula correctamente");
  }

  @DeleteMapping("/movies/{id}")
  @ApiOperation("Permite eliminar una pelicula-serie")
  public ResponseEntity<String> eliminarPeliculaSerie(@PathVariable Integer id) {
    peliculaSerieService.eliminar(id);
    return ResponseEntity.ok().body("La pelicula o serie se elimino correctamente");
  }

  @PostMapping("/movies")
  @ApiOperation("Permite crear una nueva pelicula-serie")
  public ResponseEntity<String> crearPeliculaSerie(@RequestBody PeliculaSerie peliculaSerie){
    peliculaSerie.setPersonajes(getPersonajesDelRepositorio(peliculaSerie));
    peliculaSerieService.crear(peliculaSerie);
    return ResponseEntity.ok().body("La pelicula o serie fue creada correctamente");
  }

  private Set<Personaje> getPersonajesDelRepositorio(PeliculaSerie peliculaSerie) {
    return peliculaSerie
        .getPersonajes()
        .stream()
        .map(personaje -> personajeService.findById(personaje.getId()))
        .collect(Collectors.toSet());
  }
}
