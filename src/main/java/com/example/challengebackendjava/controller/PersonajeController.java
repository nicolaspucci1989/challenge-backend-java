package com.example.challengebackendjava.controller;

import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.serializer.View;
import com.example.challengebackendjava.service.PeliculaSerieService;
import com.example.challengebackendjava.service.PersonajeService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class PersonajeController {
  @Autowired
  PersonajeService personajeService;
  @Autowired
  PeliculaSerieService peliculaSerieService;

  @GetMapping("/characters")
  @JsonView(View.Personaje.Lista.class)
  @ApiOperation("Devuelve una lista de todos los personajes, con su id, nombre e imagen")
  public ResponseEntity<List<Personaje>> getPersonajes(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer age,
      @RequestParam(required = false) Set<Long> idMovies
      ) {

    List<Personaje> personajes = personajeService.all();

    if (name != null) {
      personajes = personajes.stream()
              .filter(personaje -> personaje.nombreCoincide(name))
              .collect(Collectors.toList());
    }

    if (age != null) {
      personajes = personajes.stream()
              .filter(personaje -> personaje.edadCoincide(age))
              .collect(Collectors.toList());
    }

    if (idMovies != null) {
      // TODO: buscar pelis del personaje
      personajes = personajes.stream()
              .filter(personaje -> personaje.estuvoEnAlgunaPelicula(idMovies))
              .collect(Collectors.toList());
    }

    return ResponseEntity.ok(personajes);
  }

  @GetMapping("/characters/{id}")
  @JsonView(View.Personaje.Detalle.class)
  @ApiOperation("Permite buscar un personaje por su id, con toda su informacion")
  public ResponseEntity<Personaje> getPersonaje(@PathVariable Long id) {
    return ResponseEntity.ok(personajeService.findById(id));
  }

  @PutMapping("/characters/{id}")
  @ApiOperation("Permite actualizar un personaje por su id")
  public ResponseEntity<String> actualizarPersonaje(@RequestBody Personaje personaje, @PathVariable Long id) {
//    personaje.setPeliculasSeries(getPeliculasDelRepositorio(personaje));
    personajeService.actualizar(personaje, id);
    return ResponseEntity.ok().body("El personaje fue actualizado correctamente");
  }

  @DeleteMapping("/characters/{id}")
  @ApiOperation("Permite eliminar un personaje")
  public ResponseEntity<String> eliminarPersonaje(@PathVariable Long id) {
    personajeService.eliminar(id);
    return ResponseEntity.ok().body("El personaje fue eliminado correctamente");
  }

  @PostMapping("/characters")
  @ApiOperation("Permite crear un nuevo personaje")
  public ResponseEntity<Personaje> crearPersonaje(@RequestBody Personaje personaje) {
//    personaje.setPeliculasSeries(getPeliculasDelRepositorio(personaje));
    return ResponseEntity.ok(personajeService.save(personaje));
  }

//  private Set<PeliculaSerie> getPeliculasDelRepositorio(Personaje personaje) {
//    return personaje
//        .getPeliculasSeries()
//        .stream()
//        .map(peliculaSerie -> peliculaSerieService.findById(peliculaSerie.getId()))
//        .collect(Collectors.toSet());
//  }
}
