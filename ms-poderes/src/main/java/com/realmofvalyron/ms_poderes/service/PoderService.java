package com.realmofvalyron.ms_poderes.service;

import com.realmofvalyron.ms_poderes.dto.PoderRequest;
import com.realmofvalyron.ms_poderes.dto.PoderResponse;
import com.realmofvalyron.ms_poderes.entity.Poder;
import com.realmofvalyron.ms_poderes.repository.PoderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PoderService {

    private final PoderRepository poderRepository;

    public PoderResponse crearPoder(PoderRequest request) {

        if (poderRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe un poder con ese nombre");
        }

        Poder poder = Poder.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .tipoRaza(request.getTipoRaza())
                .nivelRequerido(request.getNivelRequerido())
                .danio(request.getDanio())
                .costoMana(request.getCostoMana())
                .tipoPoder(Poder.TipoPoder.valueOf(request.getTipoPoder()))
                .build();

        poderRepository.save(poder);

        return mapToResponse(poder);
    }

    public List<PoderResponse> listarPoderes() {
        return poderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PoderResponse obtenerPoderPorId(Long id) {
        Poder poder = poderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poder no encontrado con id: " + id));
        return mapToResponse(poder);
    }

    public List<PoderResponse> obtenerPoderesPorRaza(String tipoRaza) {
        return poderRepository.findByTipoRaza(tipoRaza)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PoderResponse> obtenerPoderesPorNivel(Integer nivel) {
        return poderRepository.findByNivelRequeridoLessThanEqual(nivel)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void eliminarPoder(Long id) {
        if (!poderRepository.existsById(id)) {
            throw new RuntimeException("Poder no encontrado con id: " + id);
        }
        poderRepository.deleteById(id);
    }

    private PoderResponse mapToResponse(Poder poder) {
        return PoderResponse.builder()
                .id(poder.getId())
                .nombre(poder.getNombre())
                .descripcion(poder.getDescripcion())
                .tipoRaza(poder.getTipoRaza())
                .nivelRequerido(poder.getNivelRequerido())
                .danio(poder.getDanio())
                .costoMana(poder.getCostoMana())
                .tipoPoder(poder.getTipoPoder().name())
                .build();
    }

}