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
@RequestMapping("/combate")
@RequiredArgsConstructor
public class CombateController {

    private final CombateService combateService;

    @PostMapping("/iniciar")
    public ResponseEntity<BatallaResponse> iniciarBatalla(
            @Valid @RequestBody IniciarBatallaRequest request,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(combateService.iniciarBatalla(request, token));
    }

    @GetMapping("/historial/{personajeId}")
    public ResponseEntity<List<BatallaResponse>> historial(
            @PathVariable Long personajeId) {
        return ResponseEntity.ok(combateService.historialPorPersonaje(personajeId));
    }

    @GetMapping("/victorias/{personajeId}")
    public ResponseEntity<List<BatallaResponse>> victorias(
            @PathVariable Long personajeId) {
        return ResponseEntity.ok(combateService.victoriasPorPersonaje(personajeId));
    }

    @GetMapping("/victorias/{personajeId}/total")
    public ResponseEntity<Integer> totalVictorias(
            @PathVariable Long personajeId) {
        return ResponseEntity.ok(combateService.contarVictorias(personajeId));
    }

}