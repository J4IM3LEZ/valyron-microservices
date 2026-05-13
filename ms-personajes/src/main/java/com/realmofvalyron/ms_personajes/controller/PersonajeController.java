package com.realmofvalyron.ms_personajes.controller;

import com.realmofvalyron.ms_personajes.dto.PersonajeRequest;
import com.realmofvalyron.ms_personajes.dto.PersonajeResponse;
import com.realmofvalyron.ms_personajes.service.PersonajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personajes")
@RequiredArgsConstructor
public class PersonajeController {

    private final PersonajeService personajeService;

    @PostMapping
    public ResponseEntity<PersonajeResponse> crearPersonaje(@Valid @RequestBody PersonajeRequest request) {
        return ResponseEntity.ok(personajeService.crearPersonaje(request));
    }

    @GetMapping
    public ResponseEntity<List<PersonajeResponse>> listarPersonajes() {
        return ResponseEntity.ok(personajeService.listarPersonajes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonajeResponse> obtenerPersonajePorId(@PathVariable Long id) {
        return ResponseEntity.ok(personajeService.obtenerPersonajePorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersonaje(@PathVariable Long id) {
        personajeService.eliminarPersonaje(id);
        return ResponseEntity.noContent().build();
    }

}
