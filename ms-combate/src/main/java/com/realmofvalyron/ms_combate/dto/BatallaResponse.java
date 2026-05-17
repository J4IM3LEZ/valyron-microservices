package com.realmofvalyron.ms_combate.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatallaResponse {

    private Long id;
    private Long personajeId;
    private String nombrePersonaje;
    private String nombreEnemigo;
    private String tipoEnemigo;
    private Integer danioInfligido;
    private Integer danioRecibido;
    private String resultado;
    private Integer nivelPersonaje;
    private String poderUsado;
    private Long regionId;
    private LocalDateTime fechaBatalla;
    private Integer xpGanada;

}