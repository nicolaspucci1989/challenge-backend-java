package com.example.challengebackendjava.controller;


import com.example.challengebackendjava.dto.PeliculaSerieDetalleDto;
import com.example.challengebackendjava.dto.PeliculaSerieListDto;
import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.service.PeliculaSerieService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PeliculaSerieController extends BaseController {
  private final PeliculaSerieService peliculaSerieService;

  @GetMapping("/movies")
  @ApiOperation("Devuelve un listado de todas las peliculas-series con su id, nombre e imagen")
  public ResponseEntity<List<PeliculaSerieListDto>> getPeliculaSeries(
      @RequestParam(required = false) String nombre,
      @RequestParam(required = false) Long genre,
      @RequestParam(required = false) OrderEnum order
  ) {
    List<PeliculaSerieListDto> peliculasSeries = peliculaSerieService.all()
        .stream().map(PeliculaSerieListDto::fromPeliculaSerie)
        .collect(Collectors.toList());
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
  public ResponseEntity<PeliculaSerieDetalleDto> actualizarPeliculaSeire(@RequestBody PeliculaSerie peliculaSerie, @PathVariable Long id) {
    PeliculaSerie peliculaSerieActualizada = peliculaSerieService.actualizar(peliculaSerie, id);
    return ResponseEntity.ok().body(PeliculaSerieDetalleDto.fromPeliculaSerie(peliculaSerieActualizada));
  }

  @DeleteMapping("/movies/{id}")
  @ApiOperation("Permite eliminar una pelicula-serie")
  public ResponseEntity<String> eliminarPeliculaSerie(@PathVariable Long id) {
    peliculaSerieService.eliminar(id);
    return ResponseEntity.ok().body("La pelicula o serie se elimino correctamente");
  }

  @PostMapping("/movies")
  @ApiOperation("Permite crear una nueva pelicula-serie")
  public ResponseEntity<String> crearPeliculaSerie(@Valid @RequestBody PeliculaSerie peliculaSerie) {
    peliculaSerieService.save(peliculaSerie);
    return ResponseEntity.ok().body("La pelicula o serie fue creada correctamente");
  }
}
