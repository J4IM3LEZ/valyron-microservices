package com.realmofvalyron.ms_misiones.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class HistorialClient {

    private final WebClient.Builder webClientBuilder;

    public void registrarEvento(Long personajeId, String nombrePersonaje,
                                String descripcion, Long referenciaId,
                                String detalles) {
        try {
            webClientBuilder
                    .build()
                    .post()
                    .uri("http://localhost:8090/api/v1/eventos")
                    .bodyValue(Map.of(
                            "personajeId", personajeId,
                            "tipo", "MISION_COMPLETADA",
                            "descripcion", descripcion,
                            "referenciaId", referenciaId,
                            "nombrePersonaje", nombrePersonaje,
                            "detalles", detalles
                    ))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .subscribe();
        } catch (Exception e) {
            log.error("No se pudo registrar evento en historial: {}", e.getMessage(), e);
        }
    }

}