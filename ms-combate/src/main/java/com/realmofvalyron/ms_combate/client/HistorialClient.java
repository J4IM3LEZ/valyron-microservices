package com.realmofvalyron.ms_combate.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

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
                    .uri("http://localhost:8090/historial/evento")
                    .bodyValue(Map.of(
                            "personajeId", personajeId,
                            "tipo", "BATALLA",
                            "descripcion", descripcion,
                            "referenciaId", referenciaId,
                            "nombrePersonaje", nombrePersonaje,
                            "detalles", detalles
                    ))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .subscribe();
        } catch (Exception e) {
            System.out.println("No se pudo registrar evento en historial: " + e.getMessage());
        }
    }

}