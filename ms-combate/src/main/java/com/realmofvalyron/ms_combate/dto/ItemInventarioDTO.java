package com.realmofvalyron.ms_combate.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInventarioDTO {

    private Long id;
    private Long personajeId;
    private Long objetoId;
    private String nombreObjeto;
    private String tipoObjeto;
    private Boolean equipado;
    private Integer peso;

}