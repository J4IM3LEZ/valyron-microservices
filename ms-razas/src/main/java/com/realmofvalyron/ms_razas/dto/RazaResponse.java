package com.realmofvalyron.ms_razas.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RazaResponse extends RepresentationModel<RazaResponse> {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer bonusFuerza;
    private Integer bonusDestreza;
    private Integer bonusSabiduria;
    private Integer bonusVitalidad;
    private String habilidadInnata;
    private String restricciones;

}