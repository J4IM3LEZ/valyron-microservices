package com.realmofvalyron.ms_poderes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PoderRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @NotBlank(message = "El tipo de raza es obligatorio")
    private String tipoRaza;

    @NotNull(message = "El nivel requerido es obligatorio")
    private Integer nivelRequerido;

    @NotNull(message = "El danio es obligatorio")
    private Integer danio;

    @NotNull(message = "El costo de mana es obligatorio")
    private Integer costoMana;

    @NotBlank(message = "El tipo de poder es obligatorio")
    private String tipoPoder;

}