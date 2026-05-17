package com.realmofvalyron.ms_regiones.controller;

import com.realmofvalyron.ms_regiones.dto.RegionRequest;
import com.realmofvalyron.ms_regiones.dto.RegionResponse;
import com.realmofvalyron.ms_regiones.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regiones")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @PostMapping
    public ResponseEntity<RegionResponse> crear(@Valid @RequestBody RegionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(regionService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<RegionResponse>> listarTodas() {
        return ResponseEntity.ok(regionService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(regionService.buscarPorId(id));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<RegionResponse>> listarDisponibles() {
        return ResponseEntity.ok(regionService.listarDisponibles());
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<RegionResponse>> buscarPorNivel(@PathVariable Integer nivel) {
        return ResponseEntity.ok(regionService.buscarPorNivel(nivel));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<RegionResponse>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(regionService.buscarPorTipo(tipo));
    }

    @GetMapping("/{regionId}/acceso/{nivelPersonaje}")
    public ResponseEntity<RegionResponse> verificarAcceso(
            @PathVariable Long regionId,
            @PathVariable Integer nivelPersonaje) {
        return ResponseEntity.ok(regionService.verificarAcceso(regionId, nivelPersonaje));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RegionRequest request) {
        return ResponseEntity.ok(regionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        regionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}