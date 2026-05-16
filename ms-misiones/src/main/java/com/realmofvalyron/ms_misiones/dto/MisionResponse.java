package com.realmofvalyron.ms_misiones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MisionResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer nivelRequerido;
    private Integer recompensaXp;
    private Integer recompensaOro;
    private String dificultad;
    private String estadoMision;

}