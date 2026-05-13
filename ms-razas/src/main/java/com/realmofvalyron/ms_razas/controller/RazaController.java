package com.realmofvalyron.ms_razas.controller;

import com.realmofvalyron.ms_razas.dto.RazaRequest;
import com.realmofvalyron.ms_razas.dto.RazaResponse;
import com.realmofvalyron.ms_razas.service.RazaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/razas")
@RequiredArgsConstructor
public class RazaController {

    private final RazaService razaService;

    @PostMapping
    public ResponseEntity<RazaResponse> crearRaza(@Valid @RequestBody RazaRequest request) {
        return ResponseEntity.ok(razaService.crearRaza(request));
    }

    @GetMapping
    public ResponseEntity<List<RazaResponse>> listarRazas() {
        return ResponseEntity.ok(razaService.listarRazas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RazaResponse> obtenerRazaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(razaService.obtenerRazaPorId(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<RazaResponse> obtenerRazaPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(razaService.obtenerRazaPorNombre(nombre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRaza(@PathVariable Long id) {
        razaService.eliminarRaza(id);
        return ResponseEntity.noContent().build();
    }

}
