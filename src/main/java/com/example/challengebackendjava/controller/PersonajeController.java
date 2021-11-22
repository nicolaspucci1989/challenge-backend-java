package com.example.challengebackendjava.controller;

import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.serializer.View;
import com.example.challengebackendjava.service.PersonajeService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PersonajeController {
    @Autowired
    PersonajeService personajeService;

    @GetMapping("/characters")
    @JsonView(View.Personaje.Lista.class)
    public ResponseEntity<List<Personaje>> getPersonajes() {
        return ResponseEntity.ok(personajeService.all());
    }

    @GetMapping("/characters/{id}")
    @JsonView(View.Personaje.Detalle.class)
    public ResponseEntity<Personaje> getPersonaje(@PathVariable Integer id) {
        return ResponseEntity.ok(personajeService.getById(id));
    }

    @PutMapping("/characters/{id}")
    public ResponseEntity<String> actualizarPersonaje(@RequestBody Personaje personaje, @PathVariable Integer id) {
        personajeService.actualizar(personaje, id);
        return ResponseEntity.ok().body("El personaje fue actualizado correctamente");
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<String> eliminarPersonaje(@PathVariable Integer id) {
        personajeService.eliminar(id);
        return ResponseEntity.ok().body("El personaje fue eliminado correctamente");
    }

    @PostMapping("/characters")
    public ResponseEntity<String> crearPersonaje(@RequestBody Personaje personaje) {
        personajeService.crear(personaje);
        return ResponseEntity.ok().body("El personaje fue creado correctamente");
    }

}
