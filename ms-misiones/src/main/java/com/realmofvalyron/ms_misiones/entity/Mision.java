package com.realmofvalyron.ms_misiones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "misiones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Integer nivelRequerido;

    @Column(nullable = false)
    private Integer recompensaXp;

    @Column(nullable = false)
    private Integer recompensaOro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Dificultad dificultad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMision estadoMision;

    public enum Dificultad {
        FACIL,
        NORMAL,
        DIFICIL,
        LEGENDARIA
    }

    public enum EstadoMision {
        DISPONIBLE,
        EN_PROGRESO,
        COMPLETADA
    }

}