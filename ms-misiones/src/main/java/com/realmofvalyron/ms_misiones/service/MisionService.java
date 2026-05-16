package com.realmofvalyron.ms_misiones.service;

import com.realmofvalyron.ms_misiones.dto.MisionRequest;
import com.realmofvalyron.ms_misiones.dto.MisionResponse;
import com.realmofvalyron.ms_misiones.entity.Mision;
import com.realmofvalyron.ms_misiones.repository.MisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MisionService {

    private final MisionRepository misionRepository;

    public MisionResponse crearMision(MisionRequest request) {

        if (misionRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe una mision con ese nombre");
        }

        Mision mision = Mision.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .nivelRequerido(request.getNivelRequerido())
                .recompensaXp(request.getRecompensaXp())
                .recompensaOro(request.getRecompensaOro())
                .dificultad(Mision.Dificultad.valueOf(request.getDificultad()))
                .estadoMision(Mision.EstadoMision.DISPONIBLE)
                .build();

        misionRepository.save(mision);

        return mapToResponse(mision);
    }

    public List<MisionResponse> listarMisiones() {
        return misionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MisionResponse obtenerMisionPorId(Long id) {
        Mision mision = misionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mision no encontrada con id: " + id));
        return mapToResponse(mision);
    }

    public List<MisionResponse> obtenerMisionesPorNivel(Integer nivel) {
        return misionRepository.findByNivelRequeridoLessThanEqual(nivel)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MisionResponse> obtenerMisionesPorDificultad(String dificultad) {
        return misionRepository.findByDificultad(Mision.Dificultad.valueOf(dificultad))
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MisionResponse completarMision(Long id) {
        Mision mision = misionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mision no encontrada con id: " + id));

        if (mision.getEstadoMision() == Mision.EstadoMision.COMPLETADA) {
            throw new RuntimeException("La mision ya fue completada");
        }

        mision.setEstadoMision(Mision.EstadoMision.COMPLETADA);
        misionRepository.save(mision);

        return mapToResponse(mision);
    }

    public void eliminarMision(Long id) {
        if (!misionRepository.existsById(id)) {
            throw new RuntimeException("Mision no encontrada con id: " + id);
        }
        misionRepository.deleteById(id);
    }

    private MisionResponse mapToResponse(Mision mision) {
        return MisionResponse.builder()
                .id(mision.getId())
                .nombre(mision.getNombre())
                .descripcion(mision.getDescripcion())
                .nivelRequerido(mision.getNivelRequerido())
                .recompensaXp(mision.getRecompensaXp())
                .recompensaOro(mision.getRecompensaOro())
                .dificultad(mision.getDificultad().name())
                .estadoMision(mision.getEstadoMision().name())
                .build();
    }

}