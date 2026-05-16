package com.realmofvalyron.ms_objetos.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjetoResponse {

    private Long id;
    private String nombre;
    private String tipo;
    private String rareza;
    private Integer bonusFuerza;
    private Integer bonusDestreza;
    private Integer bonusSabiduría;
    private Integer bonusVida;
    private Integer nivelMinimo;
    private String razaRequerida;
    private Integer peso;
    private String descripcion;

}