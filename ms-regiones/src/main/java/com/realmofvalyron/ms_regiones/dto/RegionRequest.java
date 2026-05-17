package com.realmofvalyron.ms_regiones.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegionRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    private String descripcion;

    private String lore;

    @NotNull(message = "El nivel de peligro es obligatorio")
    @Min(value = 1, message = "El nivel de peligro debe ser al menos 1")
    @Max(value = 100, message = "El nivel de peligro no puede superar 100")
    private Integer nivelPeligro;

    @NotNull(message = "El nivel mínimo de acceso es obligatorio")
    @Min(value = 1, message = "El nivel mínimo debe ser al menos 1")
    @Max(value = 100, message = "El nivel mínimo no puede superar 100")
    private Integer nivelMinimoAcceso;

    @NotNull(message = "El tipo es obligatorio")
    private String tipo;

    private Boolean disponible;

}