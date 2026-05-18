package com.realmofvalyron.ms_historial.repository;

import com.realmofvalyron.ms_historial.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByPersonajeIdOrderByTimestampDesc(Long personajeId);

    List<Evento> findByPersonajeIdAndTipo(Long personajeId, Evento.TipoEvento tipo);

    List<Evento> findByTipoOrderByTimestampDesc(Evento.TipoEvento tipo);

    Integer countByPersonajeIdAndTipo(Long personajeId, Evento.TipoEvento tipo);

}