package com.realmofvalyron.ms_inventario.controller;

import com.realmofvalyron.ms_inventario.dto.AgregarObjetoRequest;
import com.realmofvalyron.ms_inventario.dto.InventarioResponse;
import com.realmofvalyron.ms_inventario.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @PostMapping
    public ResponseEntity<InventarioResponse> agregarObjeto(
            @Valid @RequestBody AgregarObjetoRequest request,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        InventarioResponse created = inventarioService.agregarObjeto(request, token);
        return ResponseEntity.created(java.net.URI.create("/api/v1/inventarios/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<InventarioResponse>> listarInventario(@RequestParam("personajeId") Long personajeId,
                                                                      @RequestHeader(value = "Authorization", required = false) String authHeader) {
        return ResponseEntity.ok(inventarioService.listarInventario(personajeId));
    }

    @PutMapping("/equipar/{personajeId}/{objetoId}")
    public ResponseEntity<InventarioResponse> equiparObjeto(
            @PathVariable Long personajeId,
            @PathVariable Long objetoId) {
        return ResponseEntity.ok(inventarioService.equiparObjeto(personajeId, objetoId));
    }

    @PutMapping("/desequipar/{personajeId}/{objetoId}")
    public ResponseEntity<InventarioResponse> desequiparObjeto(
            @PathVariable Long personajeId,
            @PathVariable Long objetoId) {
        return ResponseEntity.ok(inventarioService.desequiparObjeto(personajeId, objetoId));
    }

    @DeleteMapping("/{personajeId}/{objetoId}")
    public ResponseEntity<Void> eliminarObjeto(
            @PathVariable Long personajeId,
            @PathVariable Long objetoId) {
        inventarioService.eliminarObjeto(personajeId, objetoId);
        return ResponseEntity.noContent().build();
    }

}