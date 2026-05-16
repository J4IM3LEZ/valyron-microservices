package com.realmofvalyron.ms_misiones.repository;

import com.realmofvalyron.ms_misiones.entity.Mision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MisionRepository extends JpaRepository<Mision, Long> {

    Optional<Mision> findByNombre(String nombre);

    Boolean existsByNombre(String nombre);

    List<Mision> findByNivelRequeridoLessThanEqual(Integer nivel);

    List<Mision> findByDificultad(Mision.Dificultad dificultad);

    List<Mision> findByEstadoMision(Mision.EstadoMision estadoMision);

}