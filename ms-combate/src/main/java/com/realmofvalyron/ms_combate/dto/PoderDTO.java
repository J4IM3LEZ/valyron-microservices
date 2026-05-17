package com.realmofvalyron.ms_combate.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoderDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String tipoRaza;
    private Integer nivelRequerido;
    private Integer danio;
    private Integer costoMana;
    private String tipoPoder;

}