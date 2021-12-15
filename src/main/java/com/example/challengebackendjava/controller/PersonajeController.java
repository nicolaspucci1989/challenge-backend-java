package com.example.challengebackendjava.controller;

import com.example.challengebackendjava.dto.PersonajeDetalleDto;
import com.example.challengebackendjava.dto.PersonajeListaDto;
import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.service.PeliculaSerieService;
import com.example.challengebackendjava.service.PersonajeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PersonajeController {
  private final PersonajeService personajeService;
  private final PeliculaSerieService peliculaSerieService;

  @GetMapping("/characters")
  @ApiOperation("Devuelve una lista de todos los personajes, con su id, nombre e imagen")
  public ResponseEntity<List<PersonajeListaDto>> getPersonajes(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer age,
      @RequestParam(required = false) Set<Long> idMovies
  ) {

    List<PersonajeListaDto> personajes = personajeService.all()
        .stream().map(PersonajeListaDto::fromPersonaje)
        .collect(Collectors.toList());

//    if (name != null) {
//      personajes = personajes.stream()
//              .filter(personaje -> personaje.nombreCoincide(name))
//              .collect(Collectors.toList());
//    }

//    if (age != null) {
//      personajes = personajes.stream()
//              .filter(personaje -> personaje.edadCoincide(age))
//              .collect(Collectors.toList());
//    }

//    if (idMovies != null) {
//      // TODO: buscar pelis del personaje
//      personajes = personajes.stream()
//              .filter(personaje -> personaje.estuvoEnAlgunaPelicula(idMovies))
//              .collect(Collectors.toList());
//    }

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
  public ResponseEntity<Personaje> crearPersonaje(@Valid @RequestBody Personaje personaje) {
//    personaje.setPeliculasSeries(getPeliculasDelRepositorio(personaje));
    return ResponseEntity.ok(personajeService.save(personaje));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  private Set<PeliculaSerie> getPeliculasDelRepositorio(Personaje personaje) {
    return personaje
        .getPeliculasSeries()
        .stream()
        .map(peliculaSerie -> peliculaSerieService.findById(peliculaSerie.getId()))
        .collect(Collectors.toSet());
  }
}
