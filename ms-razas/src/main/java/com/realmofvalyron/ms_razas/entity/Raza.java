package com.realmofvalyron.ms_razas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "razas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Raza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    // Bonos de estadisticas raciales
    @Column(nullable = false)
    private Integer bonusFuerza;
    @Column(nullable = false)
    private Integer bonusDestreza;
    @Column(nullable = false)
    private Integer bonusSabiduria;
    @Column(nullable = false)
    private Integer bonusVitalidad;


    //Habilidad innata de la raza
    @Column(nullable = true)
    private String habilidadInnata;

    @Column(nullable = true)
    private String restricciones;




    // FIN //
}
