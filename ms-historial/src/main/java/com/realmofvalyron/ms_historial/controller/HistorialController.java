package com.realmofvalyron.ms_historial.controller;

import com.realmofvalyron.ms_historial.dto.EventoRequest;
import com.realmofvalyron.ms_historial.dto.EventoResponse;
import com.realmofvalyron.ms_historial.service.HistorialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historial")
@RequiredArgsConstructor
public class HistorialController {

    private final HistorialService historialService;

    @PostMapping("/evento")
    public ResponseEntity<EventoResponse> registrarEvento(
            @Valid @RequestBody EventoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(historialService.registrarEvento(request));
    }

    @GetMapping("/personaje/{personajeId}")
    public ResponseEntity<List<EventoResponse>> historialPorPersonaje(
            @PathVariable Long personajeId) {
        return ResponseEntity.ok(historialService.historialPorPersonaje(personajeId));
    }

    @GetMapping("/personaje/{personajeId}/tipo/{tipo}")
    public ResponseEntity<List<EventoResponse>> eventosPorTipo(
            @PathVariable Long personajeId,
            @PathVariable String tipo) {
        return ResponseEntity.ok(historialService.eventosPorTipo(personajeId, tipo));
    }

    @GetMapping("/batallas")
    public ResponseEntity<List<EventoResponse>> todasLasBatallas() {
        return ResponseEntity.ok(historialService.todosLosBatallas());
    }

    @GetMapping("/personaje/{personajeId}/tipo/{tipo}/total")
    public ResponseEntity<Integer> contarEventos(
            @PathVariable Long personajeId,
            @PathVariable String tipo) {
        return ResponseEntity.ok(historialService.contarEventosPorTipo(personajeId, tipo));
    }

}