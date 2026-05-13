package com.realmofvalyron.ms_personajes.repository;

import com.realmofvalyron.ms_personajes.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

    Optional<Personaje> findByNombre(String nombre);

    Boolean existsByNombre(String nombre);

    List<Personaje> findByRazaId(Long razaId);

}
