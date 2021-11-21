package com.example.challengebackendjava.controller;

import com.example.challengebackendjava.dao.PersonajeRepository;
import com.example.challengebackendjava.model.Personaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PersonajeController {
    @Autowired
    PersonajeRepository personajeRepo;

    @GetMapping("/characters")
    public ResponseEntity<List<Personaje>> getPersonajes() {
        return ResponseEntity.ok(personajeRepo.all());
    }
}
