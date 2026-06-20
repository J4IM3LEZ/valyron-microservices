package com.realmofvalyron.ms_combate.client;

import com.realmofvalyron.ms_combate.dto.PoderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PoderClient {

    private final WebClient.Builder webClientBuilder;

    public PoderDTO obtenerPoderPorNombre(String nombrePoder, String token) {
        try {
            List<PoderDTO> poderes = webClientBuilder
                    .build()
                    .get()
                    .uri("http://ms-poderes/api/v1/poderes")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(PoderDTO.class)
                    .collectList()
                    .block();

            if (poderes == null) return null;

            return poderes.stream()
                    .filter(p -> p.getNombre().equalsIgnoreCase(nombrePoder))
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            return null;
        }
    }

}