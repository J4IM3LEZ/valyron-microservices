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
@RequestMapping("/api/v1/eventos")
@RequiredArgsConstructor
public class HistorialController {

    private final HistorialService historialService;

    @PostMapping
    public ResponseEntity<EventoResponse> registrarEvento(@Valid @RequestBody EventoRequest request) {
        EventoResponse created = historialService.registrarEvento(request);
        return ResponseEntity.created(java.net.URI.create("/api/v1/eventos/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<EventoResponse>> listarEventos(@RequestParam(value = "personajeId", required = false) Long personajeId,
                                                               @RequestParam(value = "tipo", required = false) String tipo) {
        if (personajeId != null && tipo != null && !tipo.isBlank()) {
            return ResponseEntity.ok(historialService.eventosPorTipo(personajeId, tipo));
        }
        if (personajeId != null) {
            return ResponseEntity.ok(historialService.historialPorPersonaje(personajeId));
        }
        return ResponseEntity.ok(historialService.todosLosBatallas());
    }

    @GetMapping("/batallas")
    public ResponseEntity<List<EventoResponse>> todasLasBatallas() {
        return ResponseEntity.ok(historialService.todosLosBatallas());
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> contarEventos(@RequestParam("personajeId") Long personajeId,
                                                  @RequestParam("tipo") String tipo) {
        return ResponseEntity.ok(historialService.contarEventosPorTipo(personajeId, tipo));
    }

}