package com.realmofvalyron.ms_inventario.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjetoDTO {

    private Long id;
    private String nombre;
    private String tipo;
    private String rareza;
    private Integer bonusFuerza;
    private Integer bonusDestreza;
    private Integer bonusSabiduria;
    private Integer bonusVida;
    private Integer nivelMinimo;
    private String razaRequerida;
    private Integer peso;
    private String descripcion;

}