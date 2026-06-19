package com.realmofvalyron.ms_misiones.controller;

import com.realmofvalyron.ms_misiones.assembler.MisionModelAssembler;
import com.realmofvalyron.ms_misiones.dto.MisionRequest;
import com.realmofvalyron.ms_misiones.dto.MisionResponse;
import com.realmofvalyron.ms_misiones.service.MisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/misiones")
@RequiredArgsConstructor
public class MisionController {

    private final MisionService misionService;
    private final MisionModelAssembler assembler;

    @PostMapping
    public ResponseEntity<MisionResponse> crearMision(@Valid @RequestBody MisionRequest request) {
        MisionResponse created = misionService.crearMision(request);
        return ResponseEntity.created(java.net.URI.create("/api/v1/misiones/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<MisionResponse>> listarMisiones(
            @RequestParam(value = "nivel", required = false) Integer nivel,
            @RequestParam(value = "dificultad", required = false) String dificultad) {

        List<MisionResponse> misiones;

        if (nivel != null) {
            misiones = misionService.obtenerMisionesPorNivel(nivel);
        } else if (dificultad != null && !dificultad.isBlank()) {
            misiones = misionService.obtenerMisionesPorDificultad(dificultad);
        } else {
            misiones = misionService.listarMisiones();
        }

        List<MisionResponse> misionesConLinks = misiones.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(misionesConLinks);
    }

    /*
    @GetMapping
    public ResponseEntity<List<MisionResponse>> listarMisiones(
            @RequestParam(value = "nivel", required = false) Integer nivel,
            @RequestParam(value = "dificultad", required = false) String dificultad) {
        if (nivel != null) {
            return ResponseEntity.ok(misionService.obtenerMisionesPorNivel(nivel));
        }
        if (dificultad != null && !dificultad.isBlank()) {
            return ResponseEntity.ok(misionService.obtenerMisionesPorDificultad(dificultad));
        }
        return ResponseEntity.ok(misionService.listarMisiones());
    }
    */

    @GetMapping("/{id}")
    public ResponseEntity<MisionResponse> obtenerMisionPorId(@PathVariable Long id) {
        MisionResponse mision = misionService.obtenerMisionPorId(id);
        return ResponseEntity.ok(assembler.toModel(mision));
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<MisionResponse> obtenerMisionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(misionService.obtenerMisionPorId(id));
    }
    */
    @PutMapping("/{id}")
    public ResponseEntity<MisionResponse> actualizarMision(@PathVariable Long id, @Valid @RequestBody MisionRequest request) {
        return ResponseEntity.ok(misionService.actualizarMision(id, request));
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<MisionResponse> completarMision(
            @PathVariable Long id,
            @RequestParam Long personajeId,
            @RequestParam String nombrePersonaje) {
        return ResponseEntity.ok(misionService.completarMision(id, personajeId, nombrePersonaje));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMision(@PathVariable Long id) {
        misionService.eliminarMision(id);
        return ResponseEntity.noContent().build();
    }

}