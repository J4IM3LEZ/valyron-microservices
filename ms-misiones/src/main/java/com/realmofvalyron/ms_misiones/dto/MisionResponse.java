package com.realmofvalyron.ms_misiones.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MisionResponse extends RepresentationModel<MisionResponse> {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer nivelRequerido;
    private Integer recompensaXp;
    private Integer recompensaOro;
    private String dificultad;
    private String estadoMision;

}