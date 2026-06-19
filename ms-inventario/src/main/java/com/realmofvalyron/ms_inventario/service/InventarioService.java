package com.realmofvalyron.ms_inventario.service;

import com.realmofvalyron.ms_inventario.client.ObjetoClient;
import com.realmofvalyron.ms_inventario.dto.AgregarObjetoRequest;
import com.realmofvalyron.ms_inventario.dto.InventarioResponse;
import com.realmofvalyron.ms_inventario.dto.ObjetoDTO;
import com.realmofvalyron.ms_inventario.entity.Inventario;
import com.realmofvalyron.ms_inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final ObjetoClient objetoClient;

    private static final int PESO_MAXIMO = 100;

    public InventarioResponse agregarObjeto(AgregarObjetoRequest request, String token) {
        log.info("Intentando agregar objeto {} al inventario del personaje {}", request.getObjetoId(), request.getPersonajeId());

        // Verificar si el personaje ya tiene el objeto
        if (inventarioRepository.existsByPersonajeIdAndObjetoId(
                request.getPersonajeId(), request.getObjetoId())) {
            log.warn("Objeto {} ya existe en inventario del personaje {}", request.getObjetoId(), request.getPersonajeId());
                    throw new com.realmofvalyron.ms_inventario.exception.BadRequestException("El personaje ya tiene este objeto en su inventario");
        }

        // Llamar a ms-objetos para obtener los datos del objeto
        ObjetoDTO objeto = objetoClient.obtenerObjeto(request.getObjetoId(), token);

        if (objeto == null) {
            log.error("Objeto no encontrado: {}", request.getObjetoId());
                    throw new com.realmofvalyron.ms_inventario.exception.ResourceNotFoundException("Objeto no encontrado con id: " + request.getObjetoId());
        }

        // Verificar peso máximo del inventario
        List<Inventario> inventarioActual = inventarioRepository
                .findByPersonajeId(request.getPersonajeId());

        int pesoActual = inventarioActual.stream()
                .mapToInt(Inventario::getPeso)
                .sum();

        if (pesoActual + objeto.getPeso() > PESO_MAXIMO) {
            log.warn("Inventario lleno para personaje {}: peso actual {}, peso objeto {}", request.getPersonajeId(), pesoActual, objeto.getPeso());
                    throw new com.realmofvalyron.ms_inventario.exception.BadRequestException("El inventario está lleno. Peso máximo: "
                    + PESO_MAXIMO + ". Peso actual: " + pesoActual
                    + ". Peso del objeto: " + objeto.getPeso());
        }

        // Guardar en inventario
        Inventario inventario = Inventario.builder()
                .personajeId(request.getPersonajeId())
                .objetoId(request.getObjetoId())
                .nombreObjeto(objeto.getNombre())
                .tipoObjeto(objeto.getTipo())
                .equipado(false)
                .peso(objeto.getPeso())
                .build();

        inventarioRepository.save(inventario);
        log.info("Objeto {} agregado exitosamente al inventario del personaje {}", objeto.getNombre(), request.getPersonajeId());
        return mapToResponse(inventario);
    }

    public List<InventarioResponse> listarInventario(Long personajeId) {
        return inventarioRepository.findByPersonajeId(personajeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public InventarioResponse equiparObjeto(Long personajeId, Long objetoId) {
        Inventario inventario = inventarioRepository
                .findByPersonajeIdAndObjetoId(personajeId, objetoId)
                        .orElseThrow(() -> new com.realmofvalyron.ms_inventario.exception.ResourceNotFoundException("Objeto no encontrado en el inventario"));

        inventario.setEquipado(true);
        inventarioRepository.save(inventario);
        return mapToResponse(inventario);
    }

    public InventarioResponse desequiparObjeto(Long personajeId, Long objetoId) {
        Inventario inventario = inventarioRepository
                .findByPersonajeIdAndObjetoId(personajeId, objetoId)
                        .orElseThrow(() -> new com.realmofvalyron.ms_inventario.exception.ResourceNotFoundException("Objeto no encontrado en el inventario"));

        inventario.setEquipado(false);
        inventarioRepository.save(inventario);
        return mapToResponse(inventario);
    }

    public void eliminarObjeto(Long personajeId, Long objetoId) {
        Inventario inventario = inventarioRepository
                .findByPersonajeIdAndObjetoId(personajeId, objetoId)
                        .orElseThrow(() -> new com.realmofvalyron.ms_inventario.exception.ResourceNotFoundException("Objeto no encontrado en el inventario"));

        inventarioRepository.delete(inventario);
    }

    private InventarioResponse mapToResponse(Inventario inventario) {
        return InventarioResponse.builder()
                .id(inventario.getId())
                .personajeId(inventario.getPersonajeId())
                .objetoId(inventario.getObjetoId())
                .nombreObjeto(inventario.getNombreObjeto())
                .tipoObjeto(inventario.getTipoObjeto())
                .equipado(inventario.getEquipado())
                .peso(inventario.getPeso())
                .fechaAdquisicion(inventario.getFechaAdquisicion())
                .build();
    }

}