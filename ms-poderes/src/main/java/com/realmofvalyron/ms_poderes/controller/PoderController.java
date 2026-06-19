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
@RequestMapping("/api/v1/poderes")
@RequiredArgsConstructor
public class PoderController {

    private final PoderService poderService;

    @PostMapping
    public ResponseEntity<PoderResponse> crearPoder(@Valid @RequestBody PoderRequest request) {
        PoderResponse created = poderService.crearPoder(request);
        return ResponseEntity.created(java.net.URI.create("/api/v1/poderes/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<PoderResponse>> listarPoderes(
            @RequestParam(value = "raza", required = false) String raza,
            @RequestParam(value = "nivel", required = false) Integer nivel) {
        if (raza != null && !raza.isBlank()) {
            return ResponseEntity.ok(poderService.obtenerPoderesPorRaza(raza));
        }
        if (nivel != null) {
            return ResponseEntity.ok(poderService.obtenerPoderesPorNivel(nivel));
        }
        return ResponseEntity.ok(poderService.listarPoderes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoderResponse> obtenerPoderPorId(@PathVariable Long id) {
        return ResponseEntity.ok(poderService.obtenerPoderPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PoderResponse> actualizarPoder(@PathVariable Long id, @Valid @RequestBody PoderRequest request) {
        return ResponseEntity.ok(poderService.actualizarPoder(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPoder(@PathVariable Long id) {
        poderService.eliminarPoder(id);
        return ResponseEntity.noContent().build();
    }

}