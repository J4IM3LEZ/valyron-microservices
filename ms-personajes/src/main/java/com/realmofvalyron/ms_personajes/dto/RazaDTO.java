package com.realmofvalyron.ms_personajes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RazaDTO {

    private Long id;
    private String nombre;
    private Integer bonusFuerza;
    private Integer bonusDestreza;
    private Integer bonusSabiduria;
    private Integer bonusVitalidad;
    private String habilidadInnata;
    private String restricciones;

}