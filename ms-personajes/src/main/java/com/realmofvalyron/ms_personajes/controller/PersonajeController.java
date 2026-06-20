package com.realmofvalyron.ms_personajes.controller;

import com.realmofvalyron.ms_personajes.assembler.PersonajeModelAssembler;
import com.realmofvalyron.ms_personajes.dto.PersonajeRequest;
import com.realmofvalyron.ms_personajes.dto.PersonajeResponse;
import com.realmofvalyron.ms_personajes.service.PersonajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personajes")
@RequiredArgsConstructor
public class PersonajeController {

    private final PersonajeService personajeService;
    private final PersonajeModelAssembler assembler;

    @PostMapping
    public ResponseEntity<PersonajeResponse> crearPersonaje(@Valid @RequestBody PersonajeRequest request) {
        PersonajeResponse created = personajeService.crearPersonaje(request);
        return ResponseEntity.created(java.net.URI.create("/api/v1/personajes/" + created.getId())).body(created);
    }


    @GetMapping
    public ResponseEntity<List<PersonajeResponse>> listarPersonajes(@RequestParam(value = "nombre", required = false) String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            PersonajeResponse personaje = personajeService.obtenerPersonajePorNombre(nombre);
            return ResponseEntity.ok(java.util.List.of(assembler.toModel(personaje)));
        }

        List<PersonajeResponse> personajesConLinks = personajeService.listarPersonajes().stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(personajesConLinks);
    }

    /*
    @GetMapping
    public ResponseEntity<List<PersonajeResponse>> listarPersonajes(@RequestParam(value = "nombre", required = false) String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            // assuming service will throw if not found
            return ResponseEntity.ok(java.util.List.of(personajeService.obtenerPersonajePorNombre(nombre)));
        }
        return ResponseEntity.ok(personajeService.listarPersonajes());
    }
    */


    @GetMapping("/{id}")
    public ResponseEntity<PersonajeResponse> obtenerPersonajePorId(@PathVariable Long id) {
        PersonajeResponse personaje = personajeService.obtenerPersonajePorId(id);
        return ResponseEntity.ok(assembler.toModel(personaje));
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<PersonajeResponse> obtenerPersonajePorId(@PathVariable Long id) {
        return ResponseEntity.ok(personajeService.obtenerPersonajePorId(id));
    }
    */

    @PutMapping("/{id}")
    public ResponseEntity<PersonajeResponse> actualizarPersonaje(@PathVariable Long id, @Valid @RequestBody PersonajeRequest request) {
        return ResponseEntity.ok(personajeService.actualizarPersonaje(id, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonajeResponse> actualizarPersonaje(@PathVariable Long id, @Valid @RequestBody PersonajeRequest request) {
        return ResponseEntity.ok(personajeService.actualizarPersonaje(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersonaje(@PathVariable Long id) {
        personajeService.eliminarPersonaje(id);
        return ResponseEntity.noContent().build();
    }

}
