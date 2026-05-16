package com.realmofvalyron.ms_inventario.repository;

import com.realmofvalyron.ms_inventario.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    List<Inventario> findByPersonajeId(Long personajeId);

    List<Inventario> findByPersonajeIdAndEquipado(Long personajeId, Boolean equipado);

    Optional<Inventario> findByPersonajeIdAndObjetoId(Long personajeId, Long objetoId);

    Boolean existsByPersonajeIdAndObjetoId(Long personajeId, Long objetoId);

    Integer countByPersonajeId(Long personajeId);

}