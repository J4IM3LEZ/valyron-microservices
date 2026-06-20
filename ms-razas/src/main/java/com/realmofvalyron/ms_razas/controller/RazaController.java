package com.realmofvalyron.ms_razas.controller;

import com.realmofvalyron.ms_razas.assembler.RazaModelAssembler;
import com.realmofvalyron.ms_razas.dto.RazaRequest;
import com.realmofvalyron.ms_razas.dto.RazaResponse;
import com.realmofvalyron.ms_razas.service.RazaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/razas")
@RequiredArgsConstructor
public class RazaController {

    private final RazaService razaService;
    private final RazaModelAssembler assembler;

    @PostMapping
    public ResponseEntity<RazaResponse> crearRaza(@Valid @RequestBody RazaRequest request) {
        RazaResponse created = razaService.crearRaza(request);
        return ResponseEntity.created(java.net.URI.create("/api/v1/razas/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<RazaResponse>> listarRazas(@RequestParam(value = "nombre", required = false) String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            RazaResponse raza = razaService.obtenerRazaPorNombre(nombre);
            // Transformamos la raza única con el assembler antes de meterla en la lista
            return ResponseEntity.ok(java.util.List.of(assembler.toModel(raza)));
        }

        // Convertimos la lista completa: mapeamos cada raza a través del assembler
        List<RazaResponse> razasConLinks = razaService.listarRazas().stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(razasConLinks);
    }

    /*
    @GetMapping
    public ResponseEntity<List<RazaResponse>> listarRazas(@RequestParam(value = "nombre", required = false) String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            return ResponseEntity.ok(java.util.List.of(razaService.obtenerRazaPorNombre(nombre)));
        }
        return ResponseEntity.ok(razaService.listarRazas());
    }
    */
    @GetMapping("/{id}")
    public ResponseEntity<RazaResponse> obtenerRazaPorId(@PathVariable Long id) {
        RazaResponse raza = razaService.obtenerRazaPorId(id);
        return ResponseEntity.ok(assembler.toModel(raza));
    }

   /*
    @GetMapping("/{id}")
    public ResponseEntity<RazaResponse> obtenerRazaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(razaService.obtenerRazaPorId(id));
    }
        */
    @PutMapping("/{id}")
    public ResponseEntity<RazaResponse> actualizarRaza(@PathVariable Long id, @Valid @RequestBody RazaRequest request) {
        return ResponseEntity.ok(razaService.actualizarRaza(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRaza(@PathVariable Long id) {
        razaService.eliminarRaza(id);
        return ResponseEntity.noContent().build();
    }

}
