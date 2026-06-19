package com.realmofvalyron.ms_combate.controller;

import com.realmofvalyron.ms_combate.dto.BatallaResponse;
import com.realmofvalyron.ms_combate.dto.IniciarBatallaRequest;
import com.realmofvalyron.ms_combate.service.CombateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/combates")
@RequiredArgsConstructor
public class CombateController {

    private final CombateService combateService;

    @PostMapping
    public ResponseEntity<BatallaResponse> iniciarBatalla(
            @Valid @RequestBody IniciarBatallaRequest request,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        BatallaResponse created = combateService.iniciarBatalla(request, token);
        return ResponseEntity.created(java.net.URI.create("/api/v1/combates/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<BatallaResponse>> historial(@RequestParam("personajeId") Long personajeId) {
        return ResponseEntity.ok(combateService.historialPorPersonaje(personajeId));
    }

    @GetMapping("/victorias")
    public ResponseEntity<List<BatallaResponse>> victorias(@RequestParam("personajeId") Long personajeId) {
        return ResponseEntity.ok(combateService.victoriasPorPersonaje(personajeId));
    }

    @GetMapping("/victorias/total")
    public ResponseEntity<Integer> totalVictorias(@RequestParam("personajeId") Long personajeId) {
        return ResponseEntity.ok(combateService.contarVictorias(personajeId));
    }

}