package com.example.challengebackendjava.controller;


import com.example.challengebackendjava.dto.PeliculaSerieDetalleDto;
import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.service.GeneroService;
import com.example.challengebackendjava.service.PeliculaSerieService;
import com.example.challengebackendjava.service.PersonajeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PeliculaSerieController {
  private final PeliculaSerieService peliculaSerieService;
  private final PersonajeService personajeService;
  private final GeneroService generoService;

  @GetMapping("/movies")
  @ApiOperation("Devuelve un listado de todas las peliculas-series con su id, nombre e imagen")
  public ResponseEntity<List<PeliculaSerieDetalleDto>> getPeliculaSeries(
      @RequestParam(required = false) String nombre,
      @RequestParam(required = false) Long genre,
      @RequestParam(required = false) OrderEnum order
  ) {
    List<PeliculaSerieDetalleDto> peliculasSeries = peliculaSerieService.all()
        .stream().map(PeliculaSerieDetalleDto::fromPeliculaSerie)
        .collect(Collectors.toList());
//
//    if (nombre != null) {
//      peliculasSeries = peliculasSeries.stream()
//          .filter(peliculaSerie -> peliculaSerie.nombreCoincide(nombre))
//          .collect(Collectors.toList());
//    }
//
//    if (genre != null) {
//      Genero genero = generoService.findById(genre);
//
//      peliculasSeries = peliculasSeries.stream()
//          .filter(genero::tienePelicula)
//          .collect(Collectors.toList());
//    }
//
//    if (order != null) {
//      Collections.sort(peliculasSeries);
//
//      if (order.equals(OrderEnum.DESC)) {
//        Collections.reverse(peliculasSeries);
//      }
//    }

    return ResponseEntity.ok(peliculasSeries);
  }

  @GetMapping("/movies/{id}")
  @ApiOperation("Permite buscar una pelicula-serie por id, con toda su informacion")
  public ResponseEntity<PeliculaSerieDetalleDto> getPeliculaSerie(@PathVariable Long id) {
    PeliculaSerie peliculaSerie = peliculaSerieService.findById(id);
    return ResponseEntity.ok(PeliculaSerieDetalleDto.fromPeliculaSerie(peliculaSerie));
  }

  @PutMapping("/movies/{id}")
  @ApiOperation("Permite actualizar una pelicula-serie")
  public ResponseEntity<String> actualizarPeliculaSeire(@RequestBody PeliculaSerie peliculaSerie, @PathVariable Long id) {
    peliculaSerie.setPersonajes(getPersonajesDelRepositorio(peliculaSerie));
    peliculaSerieService.actualizar(peliculaSerie, id);
    return ResponseEntity.ok().body("Se actualizo la pelicula correctamente");
  }

  @DeleteMapping("/movies/{id}")
  @ApiOperation("Permite eliminar una pelicula-serie")
  public ResponseEntity<String> eliminarPeliculaSerie(@PathVariable Long id) {
    peliculaSerieService.eliminar(id);
    return ResponseEntity.ok().body("La pelicula o serie se elimino correctamente");
  }

  @PostMapping("/movies")
  @ApiOperation("Permite crear una nueva pelicula-serie")
  public ResponseEntity<String> crearPeliculaSerie(@RequestBody PeliculaSerie peliculaSerie){
    peliculaSerie.setPersonajes(getPersonajesDelRepositorio(peliculaSerie));
    peliculaSerieService.save(peliculaSerie);
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
