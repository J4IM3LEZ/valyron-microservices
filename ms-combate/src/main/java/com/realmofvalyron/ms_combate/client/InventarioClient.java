package com.realmofvalyron.ms_combate.client;

import com.realmofvalyron.ms_combate.dto.ItemInventarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventarioClient {

    private final WebClient.Builder webClientBuilder;

    public List<ItemInventarioDTO> obtenerInventario(Long personajeId, String token) {
        try {
            return webClientBuilder
                    .build()
                    .get()
                    .uri("http://localhost:8084/api/v1/inventarios?personajeId={id}", personajeId)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(ItemInventarioDTO.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            return List.of();
        }
    }

}