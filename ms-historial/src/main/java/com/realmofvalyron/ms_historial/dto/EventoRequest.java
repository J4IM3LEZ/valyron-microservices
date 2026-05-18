package com.realmofvalyron.ms_historial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventoRequest {

    @NotNull(message = "El personajeId es obligatorio")
    private Long personajeId;

    @NotNull(message = "El tipo es obligatorio")
    private String tipo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private Long referenciaId;

    private String nombrePersonaje;

    private String detalles;

}