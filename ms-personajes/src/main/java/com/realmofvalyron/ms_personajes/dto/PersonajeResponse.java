package com.realmofvalyron.ms_personajes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonajeResponse {

    private Long id;
    private String nombre;
    private Long razaId;
    private String razaNombre;
    private Integer nivel;
    private Integer experiencia;
    private Integer fuerza;
    private Integer destreza;
    private Integer sabiduria;
    private Integer vitalidad;
    private Integer oro;
    private String estado;

}