package com.realmofvalyron.ms_razas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RazaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @NotNull(message = "El bonus de fuerza es obligatorio")
    private Integer bonusFuerza;

    @NotNull(message = "El bonus de destreza es obligatorio")
    private Integer bonusDestreza;

    @NotNull(message = "El bonus de sabiduria es obligatorio")
    private Integer bonusSabiduria;

    @NotNull(message = "El bonus de vitalidad es obligatorio")
    private Integer bonusVitalidad;

    @NotBlank(message = "La habilidad innata es obligatoria")
    private String habilidadInnata;

    private String restricciones;

}
