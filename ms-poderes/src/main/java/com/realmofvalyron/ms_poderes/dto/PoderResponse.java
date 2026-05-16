package com.realmofvalyron.ms_poderes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoderResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private String tipoRaza;
    private Integer nivelRequerido;
    private Integer danio;
    private Integer costoMana;
    private String tipoPoder;

}