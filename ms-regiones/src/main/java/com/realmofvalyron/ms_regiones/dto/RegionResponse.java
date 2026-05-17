package com.realmofvalyron.ms_regiones.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private String lore;
    private Integer nivelPeligro;
    private Integer nivelMinimoAcceso;
    private String tipo;
    private Boolean disponible;

}