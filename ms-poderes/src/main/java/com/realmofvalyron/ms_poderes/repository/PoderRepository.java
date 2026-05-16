package com.realmofvalyron.ms_poderes.repository;

import com.realmofvalyron.ms_poderes.entity.Poder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PoderRepository extends JpaRepository<Poder, Long> {

    Optional<Poder> findByNombre(String nombre);

    Boolean existsByNombre(String nombre);

    List<Poder> findByTipoRaza(String tipoRaza);

    List<Poder> findByNivelRequeridoLessThanEqual(Integer nivel);

}