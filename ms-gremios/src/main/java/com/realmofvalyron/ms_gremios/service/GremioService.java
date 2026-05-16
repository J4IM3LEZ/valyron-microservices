package com.realmofvalyron.ms_gremios.service;

import com.realmofvalyron.ms_gremios.dto.GremioRequest;
import com.realmofvalyron.ms_gremios.dto.GremioResponse;
import com.realmofvalyron.ms_gremios.entity.Gremio;
import com.realmofvalyron.ms_gremios.repository.GremioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GremioService {

    private final GremioRepository gremioRepository;

    public GremioResponse crearGremio(GremioRequest request) {

        if (gremioRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe un gremio con ese nombre");
        }

        Gremio gremio = Gremio.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .guardianId(request.getGuardianId())
                .tesoroOro(0)
                .nivelGremio(1)
                .estadoGremio(Gremio.EstadoGremio.ACTIVO)
                .build();

        gremioRepository.save(gremio);

        return mapToResponse(gremio);
    }

    public List<GremioResponse> listarGremios() {
        return gremioRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public GremioResponse obtenerGremioPorId(Long id) {
        Gremio gremio = gremioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gremio no encontrado con id: " + id));
        return mapToResponse(gremio);
    }

    public GremioResponse agregarTesoro(Long id, Integer oro) {
        Gremio gremio = gremioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gremio no encontrado con id: " + id));
        gremio.setTesoroOro(gremio.getTesoroOro() + oro);
        gremioRepository.save(gremio);
        return mapToResponse(gremio);
    }

    public GremioResponse disolverGremio(Long id) {
        Gremio gremio = gremioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gremio no encontrado con id: " + id));

        if (gremio.getEstadoGremio() == Gremio.EstadoGremio.DISUELTO) {
            throw new RuntimeException("El gremio ya fue disuelto");
        }

        gremio.setEstadoGremio(Gremio.EstadoGremio.DISUELTO);
        gremioRepository.save(gremio);
        return mapToResponse(gremio);
    }

    public void eliminarGremio(Long id) {
        if (!gremioRepository.existsById(id)) {
            throw new RuntimeException("Gremio no encontrado con id: " + id);
        }
        gremioRepository.deleteById(id);
    }

    private GremioResponse mapToResponse(Gremio gremio) {
        return GremioResponse.builder()
                .id(gremio.getId())
                .nombre(gremio.getNombre())
                .descripcion(gremio.getDescripcion())
                .guardianId(gremio.getGuardianId())
                .tesoroOro(gremio.getTesoroOro())
                .nivelGremio(gremio.getNivelGremio())
                .estadoGremio(gremio.getEstadoGremio().name())
                .build();
    }

}