package com.realmofvalyron.ms_misiones.controller;

import com.realmofvalyron.ms_misiones.dto.MisionRequest;
import com.realmofvalyron.ms_misiones.dto.MisionResponse;
import com.realmofvalyron.ms_misiones.service.MisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/misiones")
@RequiredArgsConstructor
public class MisionController {

    private final MisionService misionService;

    @PostMapping
    public ResponseEntity<MisionResponse> crearMision(@Valid @RequestBody MisionRequest request) {
        return ResponseEntity.ok(misionService.crearMision(request));
    }

    @GetMapping
    public ResponseEntity<List<MisionResponse>> listarMisiones() {
        return ResponseEntity.ok(misionService.listarMisiones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MisionResponse> obtenerMisionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(misionService.obtenerMisionPorId(id));
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<MisionResponse>> obtenerMisionesPorNivel(@PathVariable Integer nivel) {
        return ResponseEntity.ok(misionService.obtenerMisionesPorNivel(nivel));
    }

    @GetMapping("/dificultad/{dificultad}")
    public ResponseEntity<List<MisionResponse>> obtenerMisionesPorDificultad(@PathVariable String dificultad) {
        return ResponseEntity.ok(misionService.obtenerMisionesPorDificultad(dificultad));
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