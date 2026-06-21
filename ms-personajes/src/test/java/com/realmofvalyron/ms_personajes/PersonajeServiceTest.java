package com.realmofvalyron.ms_personajes;

import com.realmofvalyron.ms_personajes.client.RazaClient;
import com.realmofvalyron.ms_personajes.dto.PersonajeRequest;
import com.realmofvalyron.ms_personajes.dto.PersonajeResponse;
import com.realmofvalyron.ms_personajes.dto.RazaDTO;
import com.realmofvalyron.ms_personajes.entity.Personaje;
import com.realmofvalyron.ms_personajes.exception.BadRequestException;
import com.realmofvalyron.ms_personajes.exception.ResourceNotFoundException;
import com.realmofvalyron.ms_personajes.repository.PersonajeRepository;
import com.realmofvalyron.ms_personajes.service.PersonajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class PersonajeServiceTest {

    @Autowired
    private PersonajeService personajeService;

    @MockitoBean
    private PersonajeRepository personajeRepository;

    @MockitoBean
    private RazaClient razaClient;

    private Personaje personaje;
    private RazaDTO razaDTO;
    private PersonajeRequest personajeRequest;

    @BeforeEach
    void setUp() {
        razaDTO = new RazaDTO();
        razaDTO.setId(1L);
        razaDTO.setNombre("Elfico");
        razaDTO.setBonusFuerza(2);
        razaDTO.setBonusDestreza(5);
        razaDTO.setBonusSabiduria(5);
        razaDTO.setBonusVitalidad(3);

        personaje = Personaje.builder()
                .id(1L)
                .nombre("Legolas")
                .razaId(1L)
                .nivel(1)
                .experiencia(0)
                .fuerza(12)
                .destreza(15)
                .sabiduria(15)
                .vitalidad(13)
                .oro(100)
                .estado(Personaje.Estado.ACTIVO)
                .build();

        personajeRequest = new PersonajeRequest();
        personajeRequest.setNombre("Legolas");
        personajeRequest.setRazaId(1L);
    }

    @Test
    void crearPersonaje_Exitoso() {
        when(personajeRepository.existsByNombre(personajeRequest.getNombre())).thenReturn(false);
        when(razaClient.obtenerRazaPorId(personajeRequest.getRazaId())).thenReturn(razaDTO);
        when(personajeRepository.save(any(Personaje.class))).thenReturn(personaje);

        PersonajeResponse response = personajeService.crearPersonaje(personajeRequest);

        assertNotNull(response);
        assertEquals("Legolas", response.getNombre());
        assertEquals("Elfico", response.getRazaNombre());
        assertEquals(15, response.getDestreza());
        verify(personajeRepository, times(1)).save(any(Personaje.class));
    }

    @Test
    void crearPersonaje_CuandoYaExisteNombre_DeberiaLanzarBadRequestException() {
        when(personajeRepository.existsByNombre(personajeRequest.getNombre())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> personajeService.crearPersonaje(personajeRequest));
        verify(personajeRepository, never()).save(any(Personaje.class));
    }

    @Test
    void crearPersonaje_CuandoRazaNoExiste_DeberiaLanzarResourceNotFoundException() {
        when(personajeRepository.existsByNombre(personajeRequest.getNombre())).thenReturn(false);
        when(razaClient.obtenerRazaPorId(personajeRequest.getRazaId())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> personajeService.crearPersonaje(personajeRequest));
        verify(personajeRepository, never()).save(any(Personaje.class));
    }

    @Test
    void obtenerPersonajePorId_Exitoso() {
        when(personajeRepository.findById(1L)).thenReturn(Optional.of(personaje));
        when(razaClient.obtenerRazaPorId(1L)).thenReturn(razaDTO);

        PersonajeResponse response = personajeService.obtenerPersonajePorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Legolas", response.getNombre());
        assertEquals("Elfico", response.getRazaNombre());
    }

    @Test
    void obtenerPersonajePorId_NoEncontrado_LanzaException() {
        when(personajeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> personajeService.obtenerPersonajePorId(99L));

        verify(razaClient, never()).obtenerRazaPorId(anyLong());
    }

    @Test
    void eliminarPersonaje_CuandoExiste_DeberiaEliminarExitosamente() {
        when(personajeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(personajeRepository).deleteById(1L);

        personajeService.eliminarPersonaje(1L);

        verify(personajeRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarPersonaje_CuandoNoExiste_DeberiaLanzarResourceNotFoundException() {
        when(personajeRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> personajeService.eliminarPersonaje(99L));
        verify(personajeRepository, never()).deleteById(any());
    }

}