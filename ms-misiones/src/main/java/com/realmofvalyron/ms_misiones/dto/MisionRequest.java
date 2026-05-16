package com.realmofvalyron.ms_misiones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MisionRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @NotNull(message = "El nivel requerido es obligatorio")
    private Integer nivelRequerido;

    @NotNull(message = "La recompensa de XP es obligatoria")
    private Integer recompensaXp;

    @NotNull(message = "La recompensa de oro es obligatoria")
    private Integer recompensaOro;

    @NotBlank(message = "La dificultad es obligatoria")
    private String dificultad;

}