package com.realmofvalyron.ms_combate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "batallas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Batalla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long personajeId;

    @NotBlank
    @Column(nullable = false)
    private String nombrePersonaje;

    @NotBlank
    @Column(nullable = false)
    private String nombreEnemigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEnemigo tipoEnemigo;

    @Min(0)
    @Column(nullable = false)
    private Integer danioInfligido;

    @Min(0)
    @Column(nullable = false)
    private Integer danioRecibido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultadoBatalla resultado;

    @Column(nullable = false)
    private Integer nivelPersonaje;

    private String poderUsado;

    private Long regionId;

    @Column(nullable = false)
    private LocalDateTime fechaBatalla;

    @Min(0)
    @Column(nullable = false)
    private Integer xpGanada;

    @PrePersist
    public void prePersist() {
        this.fechaBatalla = LocalDateTime.now();
    }

    public enum TipoEnemigo {
        GOBLIN,
        ORCO,
        TROLL,
        DRAGON,
        NAZGUL,
        ARANA,
        ESQUELETO,
        JEFE
    }

    public enum ResultadoBatalla {
        VICTORIA,
        DERROTA,
        EMPATE
    }

}