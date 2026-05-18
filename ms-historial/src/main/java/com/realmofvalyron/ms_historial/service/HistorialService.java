package com.realmofvalyron.ms_historial.service;

import com.realmofvalyron.ms_historial.dto.EventoRequest;
import com.realmofvalyron.ms_historial.dto.EventoResponse;
import com.realmofvalyron.ms_historial.entity.Evento;
import com.realmofvalyron.ms_historial.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorialService {

    private final EventoRepository eventoRepository;

    public EventoResponse registrarEvento(EventoRequest request) {
        Evento evento = Evento.builder()
                .personajeId(request.getPersonajeId())
                .tipo(Evento.TipoEvento.valueOf(request.getTipo()))
                .descripcion(request.getDescripcion())
                .referenciaId(request.getReferenciaId())
                .nombrePersonaje(request.getNombrePersonaje())
                .detalles(request.getDetalles())
                .build();

        eventoRepository.save(evento);
        return mapToResponse(evento);
    }

    public List<EventoResponse> historialPorPersonaje(Long personajeId) {
        return eventoRepository
                .findByPersonajeIdOrderByTimestampDesc(personajeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<EventoResponse> eventosPorTipo(Long personajeId, String tipo) {
        return eventoRepository
                .findByPersonajeIdAndTipo(personajeId, Evento.TipoEvento.valueOf(tipo))
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<EventoResponse> todosLosBatallas() {
        return eventoRepository
                .findByTipoOrderByTimestampDesc(Evento.TipoEvento.BATALLA)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Integer contarEventosPorTipo(Long personajeId, String tipo) {
        return eventoRepository.countByPersonajeIdAndTipo(
                personajeId, Evento.TipoEvento.valueOf(tipo));
    }

    private EventoResponse mapToResponse(Evento evento) {
        return EventoResponse.builder()
                .id(evento.getId())
                .personajeId(evento.getPersonajeId())
                .tipo(evento.getTipo().name())
                .descripcion(evento.getDescripcion())
                .timestamp(evento.getTimestamp())
                .referenciaId(evento.getReferenciaId())
                .nombrePersonaje(evento.getNombrePersonaje())
                .detalles(evento.getDetalles())
                .build();
    }

}