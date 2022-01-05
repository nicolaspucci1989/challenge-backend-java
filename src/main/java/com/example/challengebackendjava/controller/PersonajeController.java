package com.example.challengebackendjava.controller;

import com.example.challengebackendjava.dao.CriterioDeBusquedaPersonaje;
import com.example.challengebackendjava.dto.PersonajeDetalleDto;
import com.example.challengebackendjava.dto.PersonajeListaDto;
import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.service.PersonajeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class PersonajeController extends BaseController {
  private final PersonajeService personajeService;

  @GetMapping("/characters")
  @ApiOperation("Devuelve una lista de todos los personajes, con su id, nombre e imagen")
  public ResponseEntity<List<PersonajeListaDto>> getPersonajes(
      @RequestParam(required = false) Optional<String> name,
      @RequestParam(required = false) Optional<Integer> age,
      @RequestParam(required = false) List<Long> movies,
      @RequestParam(required = false) Optional<Float> peso
  ) {
    CriterioDeBusquedaPersonaje criterio = CriterioDeBusquedaPersonaje.builder()
        .name(name)
        .edad(age)
        .idPelis(movies)
        .peso(peso)
        .build();

    List<PersonajeListaDto> personajes = personajeService.all(criterio)
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
