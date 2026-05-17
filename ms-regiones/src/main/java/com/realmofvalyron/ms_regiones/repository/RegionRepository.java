package com.realmofvalyron.ms_regiones.repository;

import com.realmofvalyron.ms_regiones.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByNombre(String nombre);

    Boolean existsByNombre(String nombre);

    List<Region> findByDisponible(Boolean disponible);

    List<Region> findByTipo(Region.TipoRegion tipo);

    List<Region> findByNivelMinimoAccesoLessThanEqual(Integer nivel);

    List<Region> findByNivelPeligroLessThanEqual(Integer nivelPeligro);

}