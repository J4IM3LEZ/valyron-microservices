package com.realmofvalyron.ms_combate.service;

import com.realmofvalyron.ms_combate.client.HistorialClient;
import com.realmofvalyron.ms_combate.client.InventarioClient;
import com.realmofvalyron.ms_combate.client.PersonajeClient;
import com.realmofvalyron.ms_combate.client.PoderClient;
import com.realmofvalyron.ms_combate.dto.BatallaResponse;
import com.realmofvalyron.ms_combate.dto.IniciarBatallaRequest;
import com.realmofvalyron.ms_combate.dto.ItemInventarioDTO;
import com.realmofvalyron.ms_combate.dto.PersonajeDTO;
import com.realmofvalyron.ms_combate.dto.PoderDTO;
import com.realmofvalyron.ms_combate.entity.Batalla;
import com.realmofvalyron.ms_combate.repository.BatallaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CombateService {

    private final BatallaRepository batallaRepository;
    private final PersonajeClient personajeClient;
    private final PoderClient poderClient;
    private final InventarioClient inventarioClient;
    private final HistorialClient historialClient;
    private final Random random = new Random();

    public BatallaResponse iniciarBatalla(IniciarBatallaRequest request, String token) {

        log.info("Iniciando batalla para personaje {} contra {} tipo {}",
                request.getPersonajeId(), request.getNombreEnemigo(), request.getTipoEnemigo());

        // 1. Obtener stats del personaje desde ms-personajes
        PersonajeDTO personaje = personajeClient.obtenerPersonaje(request.getPersonajeId(), token);
        if (personaje == null) {
            log.error("Personaje no encontrado: {}", request.getPersonajeId());
            throw new com.realmofvalyron.ms_combate.exception.ResourceNotFoundException("Personaje no encontrado con id: " + request.getPersonajeId());
        }
        log.debug("Personaje cargado: {} nivel {}", personaje.getNombre(), personaje.getNivel());

        // 2. Obtener arma equipada desde ms-inventario
        int bonusArma = 0;
        try {
            List<ItemInventarioDTO> inventario = inventarioClient
                    .obtenerInventario(request.getPersonajeId(), token);

            if (inventario != null) {
                ItemInventarioDTO arma = inventario.stream()
                        .filter(item -> item.getEquipado() &&
                                item.getTipoObjeto().equals("ARMA"))
                        .findFirst()
                        .orElse(null);

                if (arma != null) {
                    bonusArma = 10;
                    log.debug("Arma equipada encontrada: {}", arma.getNombreObjeto());
                }
            }
        } catch (Exception e) {
            log.warn("Error al obtener inventario para personaje {}: {}",
                    request.getPersonajeId(), e.getMessage());
            bonusArma = 0;
        }

        // 3. Calcular bono de poder desde ms-poderes
        int bonusPoder = 0;
        String nombrePoderUsado = request.getPoderUsado();

        if (nombrePoderUsado != null && !nombrePoderUsado.isEmpty()) {
            PoderDTO poder = poderClient.obtenerPoderPorNombre(nombrePoderUsado, token);

            if (poder != null) {
                if (personaje.getNivel() >= poder.getNivelRequerido()) {
                    bonusPoder = poder.getDanio();
                    log.debug("Poder usado: {} con daño {}", poder.getNombre(), poder.getDanio());
                } else {
                    throw new com.realmofvalyron.ms_combate.exception.BadRequestException("El personaje necesita nivel " +
                            poder.getNivelRequerido() +
                            " para usar el poder " + poder.getNombre() +
                            ". Nivel actual: " + personaje.getNivel());
                }
            } else {
                bonusPoder = 15;
                log.warn("Poder {} no encontrado en ms-poderes, usando bono básico", nombrePoderUsado);
            }
        }

        // 4. Calcular daño total
        int danioInfligido = personaje.getFuerza() + bonusArma + bonusPoder + random.nextInt(10);

        // 5. Calcular daño del enemigo
        int danioEnemigo = calcularDanioEnemigo(
                Batalla.TipoEnemigo.valueOf(request.getTipoEnemigo()),
                personaje.getNivel()
        );

        // 6. Determinar resultado
        Batalla.ResultadoBatalla resultado;
        int xpGanada;

        if (danioInfligido > danioEnemigo) {
            resultado = Batalla.ResultadoBatalla.VICTORIA;
            xpGanada = 50 + (personaje.getNivel() * 10);
        } else if (danioInfligido < danioEnemigo) {
            resultado = Batalla.ResultadoBatalla.DERROTA;
            xpGanada = 10;
        } else {
            resultado = Batalla.ResultadoBatalla.EMPATE;
            xpGanada = 25;
        }

        // 7. Guardar batalla
        Batalla batalla = Batalla.builder()
                .personajeId(request.getPersonajeId())
                .nombrePersonaje(personaje.getNombre())
                .nombreEnemigo(request.getNombreEnemigo())
                .tipoEnemigo(Batalla.TipoEnemigo.valueOf(request.getTipoEnemigo()))
                .danioInfligido(danioInfligido)
                .danioRecibido(danioEnemigo)
                .resultado(resultado)
                .nivelPersonaje(personaje.getNivel())
                .poderUsado(nombrePoderUsado)
                .regionId(request.getRegionId())
                .xpGanada(xpGanada)
                .build();

        batallaRepository.save(batalla);
        log.info("Batalla registrada - Personaje: {} vs {} - Resultado: {} - XP ganada: {}",
                batalla.getNombrePersonaje(), batalla.getNombreEnemigo(),
                batalla.getResultado(), batalla.getXpGanada());

        // 8. Notificar a ms-historial
        historialClient.registrarEvento(
                batalla.getPersonajeId(),
                batalla.getNombrePersonaje(),
                batalla.getNombrePersonaje() + " " +
                        (resultado == Batalla.ResultadoBatalla.VICTORIA ? "venció a " : "fue derrotado por ") +
                        batalla.getNombreEnemigo(),
                batalla.getId(),
                "Daño infligido: " + danioInfligido +
                        ", Daño recibido: " + danioEnemigo +
                        ", XP ganada: " + xpGanada
        );

        return mapToResponse(batalla);
    }

    public List<BatallaResponse> historialPorPersonaje(Long personajeId) {
        log.info("Obteniendo historial de batallas para personaje {}", personajeId);
        return batallaRepository
                .findByPersonajeIdOrderByFechaBatallaDesc(personajeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BatallaResponse> victoriasPorPersonaje(Long personajeId) {
        log.info("Obteniendo victorias para personaje {}", personajeId);
        return batallaRepository
                .findByPersonajeIdAndResultado(personajeId, Batalla.ResultadoBatalla.VICTORIA)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Integer contarVictorias(Long personajeId) {
        return batallaRepository.countByPersonajeIdAndResultado(
                personajeId, Batalla.ResultadoBatalla.VICTORIA);
    }

    private int calcularDanioEnemigo(Batalla.TipoEnemigo tipo, int nivelPersonaje) {
        return switch (tipo) {
            case GOBLIN -> 5 + random.nextInt(5);
            case ORCO -> 15 + random.nextInt(10);
            case TROLL -> 25 + random.nextInt(15);
            case ARAÑA -> 10 + random.nextInt(8);
            case ESQUELETO -> 8 + random.nextInt(6);
            case NAZGUL -> 40 + random.nextInt(20);
            case DRAGON -> 60 + random.nextInt(30);
            case JEFE -> nivelPersonaje * 5 + random.nextInt(20);
        };
    }

    private BatallaResponse mapToResponse(Batalla batalla) {
        return BatallaResponse.builder()
                .id(batalla.getId())
                .personajeId(batalla.getPersonajeId())
                .nombrePersonaje(batalla.getNombrePersonaje())
                .nombreEnemigo(batalla.getNombreEnemigo())
                .tipoEnemigo(batalla.getTipoEnemigo().name())
                .danioInfligido(batalla.getDanioInfligido())
                .danioRecibido(batalla.getDanioRecibido())
                .resultado(batalla.getResultado().name())
                .nivelPersonaje(batalla.getNivelPersonaje())
                .poderUsado(batalla.getPoderUsado())
                .regionId(batalla.getRegionId())
                .fechaBatalla(batalla.getFechaBatalla())
                .xpGanada(batalla.getXpGanada())
                .build();
    }

}