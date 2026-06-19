package com.realmofvalyron.ms_inventario.client;

import com.realmofvalyron.ms_inventario.dto.ObjetoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ObjetoClient {

    private final WebClient.Builder webClientBuilder;

    public ObjetoDTO obtenerObjeto(Long objetoId, String token) {
        return webClientBuilder
                .build()
                .get()
                .uri("http://ms-objetos/api/v1/objetos/{id}", objetoId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ObjetoDTO.class)
                .block();
    }

}