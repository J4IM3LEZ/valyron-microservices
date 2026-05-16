package com.realmofvalyron.ms_inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long personajeId;

    @NotNull
    @Column(nullable = false)
    private Long objetoId;

    @Column(nullable = false)
    private String nombreObjeto;

    @Column(nullable = false)
    private String tipoObjeto;

    @Column(nullable = false)
    private Boolean equipado;

    @Min(0)
    @Column(nullable = false)
    private Integer peso;

    @Column(nullable = false)
    private LocalDateTime fechaAdquisicion;

    @PrePersist
    public void prePersist() {
        this.fechaAdquisicion = LocalDateTime.now();
        if (this.equipado == null) {
            this.equipado = false;
        }
    }

}