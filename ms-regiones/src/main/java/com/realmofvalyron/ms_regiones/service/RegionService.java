package com.realmofvalyron.ms_regiones.service;

import com.realmofvalyron.ms_regiones.dto.RegionRequest;
import com.realmofvalyron.ms_regiones.dto.RegionResponse;
import com.realmofvalyron.ms_regiones.entity.Region;
import com.realmofvalyron.ms_regiones.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionResponse crear(RegionRequest request) {
        if (regionRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe una región con ese nombre");
        }

        Region region = Region.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .lore(request.getLore())
                .nivelPeligro(request.getNivelPeligro())
                .nivelMinimoAcceso(request.getNivelMinimoAcceso())
                .tipo(Region.TipoRegion.valueOf(request.getTipo()))
                .disponible(request.getDisponible() != null ? request.getDisponible() : true)
                .build();

        regionRepository.save(region);
        return mapToResponse(region);
    }

    public List<RegionResponse> listarTodas() {
        return regionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RegionResponse buscarPorId(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con id: " + id));
        return mapToResponse(region);
    }

    public List<RegionResponse> buscarPorNivel(Integer nivel) {
        return regionRepository.findByNivelMinimoAccesoLessThanEqual(nivel)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<RegionResponse> buscarPorTipo(String tipo) {
        return regionRepository.findByTipo(Region.TipoRegion.valueOf(tipo))
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<RegionResponse> listarDisponibles() {
        return regionRepository.findByDisponible(true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RegionResponse actualizar(Long id, RegionRequest request) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con id: " + id));

        region.setNombre(request.getNombre());
        region.setDescripcion(request.getDescripcion());
        region.setLore(request.getLore());
        region.setNivelPeligro(request.getNivelPeligro());
        region.setNivelMinimoAcceso(request.getNivelMinimoAcceso());
        region.setTipo(Region.TipoRegion.valueOf(request.getTipo()));
        if (request.getDisponible() != null) {
            region.setDisponible(request.getDisponible());
        }

        regionRepository.save(region);
        return mapToResponse(region);
    }

    public void eliminar(Long id) {
        if (!regionRepository.existsById(id)) {
            throw new RuntimeException("Región no encontrada con id: " + id);
        }
        regionRepository.deleteById(id);
    }

    public RegionResponse verificarAcceso(Long regionId, Integer nivelPersonaje) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con id: " + regionId));

        if (nivelPersonaje < region.getNivelMinimoAcceso()) {
            throw new RuntimeException("El personaje necesita nivel " +
                    region.getNivelMinimoAcceso() +
                    " para acceder a " + region.getNombre() +
                    ". Nivel actual: " + nivelPersonaje);
        }

        return mapToResponse(region);
    }

    private RegionResponse mapToResponse(Region region) {
        return RegionResponse.builder()
                .id(region.getId())
                .nombre(region.getNombre())
                .descripcion(region.getDescripcion())
                .lore(region.getLore())
                .nivelPeligro(region.getNivelPeligro())
                .nivelMinimoAcceso(region.getNivelMinimoAcceso())
                .tipo(region.getTipo().name())
                .disponible(region.getDisponible())
                .build();
    }

}