package com.example.challengebackendjava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class PersonajeController {

    @GetMapping("/characters")
    public ResponseEntity<String> getPersonajes() {
        return ResponseEntity.ok("Listado de personajes");
    }
}
