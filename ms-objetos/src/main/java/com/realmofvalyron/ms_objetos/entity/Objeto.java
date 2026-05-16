package com.realmofvalyron.ms_objetos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "objetos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Objeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, unique = true)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoObjeto tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rareza rareza;

    @Min(0)
    @Column(nullable = false)
    private Integer bonusFuerza;

    @Min(0)
    @Column(nullable = false)
    private Integer bonusDestreza;

    @Min(0)
    @Column(nullable = false)
    private Integer bonusSabiduría;

    @Min(0)
    @Column(nullable = false)
    private Integer bonusVida;

    @Min(1)
    @Max(100)
    @Column(nullable = false)
    private Integer nivelMinimo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private RazaRequerida razaRequerida;

    @Min(0)
    @Column(nullable = false)
    private Integer peso;

    @Column(length = 500)
    private String descripcion;

    public enum TipoObjeto {
        ARMA,
        ARMADURA,
        POCION,
        ACCESORIO,
        ESCUDO
    }

    public enum Rareza {
        COMUN,
        INUSUAL,
        RARO,
        EPICO,
        LEGENDARIO
    }

    public enum RazaRequerida {
        ELFICO,
        ENANO,
        HOBBIT,
        HOMBRE,
        ORCO,
        ISTARI,
        NINGUNA
    }

}