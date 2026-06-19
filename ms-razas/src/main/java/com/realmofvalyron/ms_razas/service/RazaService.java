package com.realmofvalyron.ms_razas.service;

import com.realmofvalyron.ms_razas.dto.RazaRequest;
import com.realmofvalyron.ms_razas.dto.RazaResponse;
import com.realmofvalyron.ms_razas.entity.Raza;
import com.realmofvalyron.ms_razas.repository.RazaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RazaService {

    private final RazaRepository razaRepository;

    public RazaResponse crearRaza(RazaRequest request) {

        if (razaRepository.existsByNombre(request.getNombre())) {
                throw new com.realmofvalyron.ms_razas.exception.BadRequestException("Ya existe una raza con ese nombre");
        }

        Raza raza = Raza.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .bonusFuerza(request.getBonusFuerza())
                .bonusDestreza(request.getBonusDestreza())
                .bonusSabiduria(request.getBonusSabiduria())
                .bonusVitalidad(request.getBonusVitalidad())
                .habilidadInnata(request.getHabilidadInnata())
                .restricciones(request.getRestricciones())
                .build();

        razaRepository.save(raza);

        return mapToResponse(raza);
    }

    public List<RazaResponse> listarRazas() {
        return razaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RazaResponse obtenerRazaPorId(Long id) {
        Raza raza = razaRepository.findById(id)
                    .orElseThrow(() -> new com.realmofvalyron.ms_razas.exception.ResourceNotFoundException("Raza no encontrada con id: " + id));
        return mapToResponse(raza);
    }

    public RazaResponse obtenerRazaPorNombre(String nombre) {
        Raza raza = razaRepository.findByNombre(nombre)
                    .orElseThrow(() -> new com.realmofvalyron.ms_razas.exception.ResourceNotFoundException("Raza no encontrada con nombre: " + nombre));
        return mapToResponse(raza);
    }

    public void eliminarRaza(Long id) {
        if (!razaRepository.existsById(id)) {
                throw new com.realmofvalyron.ms_razas.exception.ResourceNotFoundException("Raza no encontrada con id: " + id);
        }
        razaRepository.deleteById(id);
    }

        public RazaResponse actualizarRaza(Long id, RazaRequest request) {
            Raza raza = razaRepository.findById(id)
                    .orElseThrow(() -> new com.realmofvalyron.ms_razas.exception.ResourceNotFoundException("Raza no encontrada con id: " + id));

            raza.setNombre(request.getNombre());
            raza.setDescripcion(request.getDescripcion());
            raza.setBonusFuerza(request.getBonusFuerza());
            raza.setBonusDestreza(request.getBonusDestreza());
            raza.setBonusSabiduria(request.getBonusSabiduria());
            raza.setBonusVitalidad(request.getBonusVitalidad());
            raza.setHabilidadInnata(request.getHabilidadInnata());
            raza.setRestricciones(request.getRestricciones());

            razaRepository.save(raza);

            return mapToResponse(raza);
        }

    private RazaResponse mapToResponse(Raza raza) {
        return RazaResponse.builder()
                .id(raza.getId())
                .nombre(raza.getNombre())
                .descripcion(raza.getDescripcion())
                .bonusFuerza(raza.getBonusFuerza())
                .bonusDestreza(raza.getBonusDestreza())
                .bonusSabiduria(raza.getBonusSabiduria())
                .bonusVitalidad(raza.getBonusVitalidad())
                .habilidadInnata(raza.getHabilidadInnata())
                .restricciones(raza.getRestricciones())
                .build();
    }

}
