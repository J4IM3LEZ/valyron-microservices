package com.realmofvalyron.ms_inventario.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioResponse {

    private Long id;
    private Long personajeId;
    private Long objetoId;
    private String nombreObjeto;
    private String tipoObjeto;
    private Boolean equipado;
    private Integer peso;
    private LocalDateTime fechaAdquisicion;

}