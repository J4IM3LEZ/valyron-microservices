package com.realmofvalyron.ms_personajes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personajes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private Long razaId;

    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Integer experiencia;

    @Column(nullable = false)
    private Integer fuerza;

    @Column(nullable = false)
    private Integer destreza;

    @Column(nullable = false)
    private Integer sabiduria;

    @Column(nullable = false)
    private Integer vitalidad;

    @Column(nullable = false)
    private Integer oro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    public enum Estado {
        ACTIVO,
        MUERTO,
        DESCANSANDO
    }

}