package com.realmofvalyron.ms_personajes.client;

import com.realmofvalyron.ms_personajes.dto.RazaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class RazaClient {

    private final WebClient.Builder webClientBuilder;

    public RazaDTO obtenerRazaPorId(Long razaId) {
        return webClientBuilder
                .build()
                .get()
                .uri("http://ms-razas/api/v1/razas/{id}", razaId)
                .retrieve()
                .bodyToMono(RazaDTO.class)
                .block();
    }

}
