package com.realmofvalyron.ms_gremios.repository;

import com.realmofvalyron.ms_gremios.entity.Gremio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GremioRepository extends JpaRepository<Gremio, Long> {

    Optional<Gremio> findByNombre(String nombre);

    Boolean existsByNombre(String nombre);

    List<Gremio> findByEstadoGremio(Gremio.EstadoGremio estadoGremio);

    List<Gremio> findByGuardianId(Long guardianId);

}