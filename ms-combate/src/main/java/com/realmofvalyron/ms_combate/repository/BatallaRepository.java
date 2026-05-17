package com.realmofvalyron.ms_combate.repository;

import com.realmofvalyron.ms_combate.entity.Batalla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatallaRepository extends JpaRepository<Batalla, Long> {

    List<Batalla> findByPersonajeId(Long personajeId);

    List<Batalla> findByPersonajeIdAndResultado(Long personajeId, Batalla.ResultadoBatalla resultado);

    List<Batalla> findByPersonajeIdOrderByFechaBatallaDesc(Long personajeId);

    Integer countByPersonajeIdAndResultado(Long personajeId, Batalla.ResultadoBatalla resultado);

}