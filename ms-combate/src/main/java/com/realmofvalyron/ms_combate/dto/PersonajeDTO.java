package com.realmofvalyron.ms_combate.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonajeDTO {

    private Long id;
    private String nombre;
    private Integer nivel;
    private Integer xp;
    private Integer fuerza;
    private Integer destreza;
    private Integer sabiduria;
    private Integer vida;
    private String raza;

}