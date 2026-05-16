package com.realmofvalyron.ms_objetos.repository;

import com.realmofvalyron.ms_objetos.entity.Objeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, Long> {

    Optional<Objeto> findByNombre(String nombre);

    Boolean existsByNombre(String nombre);

    List<Objeto> findByTipo(Objeto.TipoObjeto tipo);

    List<Objeto> findByRareza(Objeto.Rareza rareza);

    List<Objeto> findByNivelMinimoLessThanEqual(Integer nivel);

    List<Objeto> findByRazaRequerida(Objeto.RazaRequerida razaRequerida);

}