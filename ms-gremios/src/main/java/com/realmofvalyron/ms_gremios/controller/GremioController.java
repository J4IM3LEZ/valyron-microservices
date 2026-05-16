package com.realmofvalyron.ms_gremios.controller;

import com.realmofvalyron.ms_gremios.dto.GremioRequest;
import com.realmofvalyron.ms_gremios.dto.GremioResponse;
import com.realmofvalyron.ms_gremios.service.GremioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gremios")
@RequiredArgsConstructor
public class GremioController {

    private final GremioService gremioService;

    @PostMapping
    public ResponseEntity<GremioResponse> crearGremio(@Valid @RequestBody GremioRequest request) {
        return ResponseEntity.ok(gremioService.crearGremio(request));
    }

    @GetMapping
    public ResponseEntity<List<GremioResponse>> listarGremios() {
        return ResponseEntity.ok(gremioService.listarGremios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GremioResponse> obtenerGremioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(gremioService.obtenerGremioPorId(id));
    }

    @PutMapping("/{id}/tesoro")
    public ResponseEntity<GremioResponse> agregarTesoro(
            @PathVariable Long id,
            @RequestParam Integer oro) {
        return ResponseEntity.ok(gremioService.agregarTesoro(id, oro));
    }

    @PutMapping("/{id}/disolver")
    public ResponseEntity<GremioResponse> disolverGremio(@PathVariable Long id) {
        return ResponseEntity.ok(gremioService.disolverGremio(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGremio(@PathVariable Long id) {
        gremioService.eliminarGremio(id);
        return ResponseEntity.noContent().build();
    }

}