package com.realmofvalyron.ms_combate.client;

import com.realmofvalyron.ms_combate.dto.PersonajeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class PersonajeClient {

    private final WebClient.Builder webClientBuilder;

    public PersonajeDTO obtenerPersonaje(Long personajeId, String token) {
        return webClientBuilder
                .build()
                .get()
                .uri("http://ms-personajes/api/v1/personajes/{id}", personajeId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(PersonajeDTO.class)
                .block();
    }

}