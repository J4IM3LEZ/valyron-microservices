package com.realmofvalyron.ms_objetos.controller;

import com.realmofvalyron.ms_objetos.dto.ObjetoRequest;
import com.realmofvalyron.ms_objetos.dto.ObjetoResponse;
import com.realmofvalyron.ms_objetos.service.ObjetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/objetos")
@RequiredArgsConstructor
public class ObjetoController {

    private final ObjetoService objetoService;

    @PostMapping
    public ResponseEntity<ObjetoResponse> crear(@Valid @RequestBody ObjetoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(objetoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ObjetoResponse>> listarTodos() {
        return ResponseEntity.ok(objetoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjetoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(objetoService.buscarPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ObjetoResponse>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(objetoService.buscarPorTipo(tipo));
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<ObjetoResponse>> buscarPorNivel(@PathVariable Integer nivel) {
        return ResponseEntity.ok(objetoService.buscarPorNivel(nivel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjetoResponse> actualizar(@PathVariable Long id,
                                                     @Valid @RequestBody ObjetoRequest request) {
        return ResponseEntity.ok(objetoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        objetoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}