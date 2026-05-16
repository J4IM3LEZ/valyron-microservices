package com.realmofvalyron.ms_objetos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ObjetoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotNull(message = "El tipo es obligatorio")
    private String tipo;

    @NotNull(message = "La rareza es obligatoria")
    private String rareza;

    @Min(value = 0, message = "El bonus de fuerza no puede ser negativo")
    private Integer bonusFuerza;

    @Min(value = 0, message = "El bonus de destreza no puede ser negativo")
    private Integer bonusDestreza;

    @Min(value = 0, message = "El bonus de sabiduría no puede ser negativo")
    private Integer bonusSabiduría;

    @Min(value = 0, message = "El bonus de vida no puede ser negativo")
    private Integer bonusVida;

    @NotNull(message = "El nivel mínimo es obligatorio")
    @Min(value = 1, message = "El nivel mínimo debe ser al menos 1")
    @Max(value = 100, message = "El nivel mínimo no puede superar 100")
    private Integer nivelMinimo;

    private String razaRequerida;

    @Min(value = 0, message = "El peso no puede ser negativo")
    private Integer peso;

    private String descripcion;

}