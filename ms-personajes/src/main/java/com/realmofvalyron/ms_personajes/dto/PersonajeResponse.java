package com.realmofvalyron.ms_personajes.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PersonajeResponse extends RepresentationModel<PersonajeResponse> {

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