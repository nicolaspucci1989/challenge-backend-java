package com.example.challengebackendjava.controller;

import com.example.challengebackendjava.dto.PersonajeDetalleDto;
import com.example.challengebackendjava.dto.PersonajeListaDto;
import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.service.PersonajeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PersonajeController extends BaseController {
  private final PersonajeService personajeService;

  @GetMapping("/characters")
  @ApiOperation("Devuelve una lista de todos los personajes, con su id, nombre e imagen")
  public ResponseEntity<List<PersonajeListaDto>> getPersonajes(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer age,
      @RequestParam(required = false) List<Long> idMovies
  ) {

    List<PersonajeListaDto> personajes = personajeService.all()
        .stream().map(PersonajeListaDto::fromPersonaje)
        .collect(Collectors.toList());

    return ResponseEntity.ok(personajes);
  }

  @GetMapping("/characters/{id}")
  @ApiOperation("Permite buscar un personaje por su id, con toda su informacion")
  public ResponseEntity<PersonajeDetalleDto> getPersonaje(@PathVariable Long id) {
    Personaje personaje = personajeService.findById(id);
    return ResponseEntity.ok(PersonajeDetalleDto.fromPersonaje(personaje));
  }

  @PutMapping("/characters/{id}")
  @ApiOperation("Permite actualizar un personaje por su id")
  public ResponseEntity<String> actualizarPersonaje(@RequestBody Personaje personaje, @PathVariable Long id) {
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
  public ResponseEntity<Personaje> crearPersonaje(@Valid @RequestBody Personaje personaje) {
    return ResponseEntity.ok(personajeService.save(personaje));
  }
}
