package com.realmofvalyron.ms_combate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IniciarBatallaRequest {

    @NotNull(message = "El personajeId es obligatorio")
    private Long personajeId;

    @NotBlank(message = "El nombre del enemigo es obligatorio")
    private String nombreEnemigo;

    @NotBlank(message = "El tipo de enemigo es obligatorio")
    private String tipoEnemigo;

    private String poderUsado;

    private Long regionId;

}