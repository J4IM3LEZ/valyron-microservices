package com.realmofvalyron.ms_gremios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gremios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gremio {

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
    private Long guardianId;

    @Column(nullable = false)
    private Integer tesoroOro;

    @Column(nullable = false)
    private Integer nivelGremio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoGremio estadoGremio;

    public enum EstadoGremio {
        ACTIVO,
        DISUELTO
    }

}