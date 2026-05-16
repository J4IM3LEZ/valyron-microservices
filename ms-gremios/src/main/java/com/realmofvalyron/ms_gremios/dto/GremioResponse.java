package com.realmofvalyron.ms_gremios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GremioResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private Long guardianId;
    private Integer tesoroOro;
    private Integer nivelGremio;
    private String estadoGremio;

}