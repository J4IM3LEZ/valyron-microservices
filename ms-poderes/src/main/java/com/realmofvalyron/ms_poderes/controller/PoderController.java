package com.realmofvalyron.ms_poderes.controller;

import com.realmofvalyron.ms_poderes.dto.PoderRequest;
import com.realmofvalyron.ms_poderes.dto.PoderResponse;
import com.realmofvalyron.ms_poderes.service.PoderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/poderes")
@RequiredArgsConstructor
public class PoderController {

    private final PoderService poderService;

    @PostMapping
    public ResponseEntity<PoderResponse> crearPoder(@Valid @RequestBody PoderRequest request) {
        return ResponseEntity.ok(poderService.crearPoder(request));
    }

    @GetMapping
    public ResponseEntity<List<PoderResponse>> listarPoderes() {
        return ResponseEntity.ok(poderService.listarPoderes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoderResponse> obtenerPoderPorId(@PathVariable Long id) {
        return ResponseEntity.ok(poderService.obtenerPoderPorId(id));
    }

    @GetMapping("/raza/{tipoRaza}")
    public ResponseEntity<List<PoderResponse>> obtenerPoderesPorRaza(@PathVariable String tipoRaza) {
        return ResponseEntity.ok(poderService.obtenerPoderesPorRaza(tipoRaza));
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<PoderResponse>> obtenerPoderesPorNivel(@PathVariable Integer nivel) {
        return ResponseEntity.ok(poderService.obtenerPoderesPorNivel(nivel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPoder(@PathVariable Long id) {
        poderService.eliminarPoder(id);
        return ResponseEntity.noContent().build();
    }

}