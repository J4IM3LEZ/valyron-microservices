package com.realmofvalyron.ms_poderes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "poderes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Poder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String tipoRaza;

    @Column(nullable = false)
    private Integer nivelRequerido;

    @Column(nullable = false)
    private Integer danio;

    @Column(nullable = false)
    private Integer costoMana;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPoder tipoPoder;

    public enum TipoPoder {
        ATAQUE,
        DEFENSA,
        CURACION,
        SOPORTE
    }

}