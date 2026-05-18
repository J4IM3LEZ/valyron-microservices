package com.realmofvalyron.ms_historial.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoResponse {

    private Long id;
    private Long personajeId;
    private String tipo;
    private String descripcion;
    private LocalDateTime timestamp;
    private Long referenciaId;
    private String nombrePersonaje;
    private String detalles;

}