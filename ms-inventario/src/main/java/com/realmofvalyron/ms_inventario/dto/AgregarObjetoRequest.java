package com.realmofvalyron.ms_inventario.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgregarObjetoRequest {

    @NotNull(message = "El personajeId es obligatorio")
    private Long personajeId;

    @NotNull(message = "El objetoId es obligatorio")
    private Long objetoId;

}