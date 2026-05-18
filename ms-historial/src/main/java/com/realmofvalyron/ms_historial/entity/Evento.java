package com.realmofvalyron.ms_historial.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long personajeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEvento tipo;

    @NotBlank
    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private Long referenciaId;

    private String nombrePersonaje;

    private String detalles;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }

    public enum TipoEvento {
        BATALLA,
        MISION_COMPLETADA,
        SUBIDA_DE_NIVEL,
        OBJETO_ADQUIRIDO,
        PODER_APRENDIDO,
        GREMIO_UNIDO,
        REGION_EXPLORADA
    }

}