package com.realmofvalyron.ms_regiones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "regiones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(length = 500)
    private String lore;

    @Min(1)
    @Max(100)
    @Column(nullable = false)
    private Integer nivelPeligro;

    @Min(1)
    @Max(100)
    @Column(nullable = false)
    private Integer nivelMinimoAcceso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRegion tipo;

    @Column(nullable = false)
    private Boolean disponible;

    @PrePersist
    public void prePersist() {
        if (this.disponible == null) {
            this.disponible = true;
        }
    }

    public enum TipoRegion {
        BOSQUE,
        MONTANA,
        MAZMORRA,
        CIUDAD,
        DESIERTO,
        PANTANO,
        VOLCAN,
        OCEANO
    }

}