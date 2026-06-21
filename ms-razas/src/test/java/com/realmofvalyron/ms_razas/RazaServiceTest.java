package com.realmofvalyron.ms_razas;

import com.realmofvalyron.ms_razas.dto.RazaRequest;
import com.realmofvalyron.ms_razas.dto.RazaResponse;
import com.realmofvalyron.ms_razas.entity.Raza;
import com.realmofvalyron.ms_razas.exception.BadRequestException;
import com.realmofvalyron.ms_razas.exception.ResourceNotFoundException;
import com.realmofvalyron.ms_razas.repository.RazaRepository;
import com.realmofvalyron.ms_razas.service.RazaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class RazaServiceTest {

    @Autowired
    private RazaService razaService;

    @MockitoBean
    private RazaRepository razaRepository;

    private RazaRequest elfoRequest;
    private Raza elfoEntity;

    @BeforeEach
    void setUp() {
        elfoRequest = RazaRequest.builder()
                .nombre("Elfico")
                .descripcion("Seres inmortales")
                .bonusFuerza(2)
                .bonusDestreza(5)
                .bonusSabiduria(5)
                .bonusVitalidad(3)
                .habilidadInnata("Vision del Bosque")
                .build();

        elfoEntity = Raza.builder()
                .id(1L)
                .nombre("Elfico")
                .descripcion("Seres inmortales")
                .bonusFuerza(2)
                .bonusDestreza(5)
                .bonusSabiduria(5)
                .bonusVitalidad(3)
                .habilidadInnata("Vision del Bosque")
                .build();
    }

    @Test
    void crearRaza_CuandoNoExiste_DeberiaGuardarExitosamente() {
        when(razaRepository.existsByNombre(elfoRequest.getNombre())).thenReturn(false);
        when(razaRepository.save(any(Raza.class))).thenReturn(elfoEntity);

        RazaResponse respuesta = razaService.crearRaza(elfoRequest);

        assertNotNull(respuesta);
        assertEquals("Elfico", respuesta.getNombre());
        verify(razaRepository, times(1)).save(any(Raza.class));
    }

    @Test
    void crearRaza_CuandoYaExisteNombre_DeberiaLanzarBadRequestException() {
        when(razaRepository.existsByNombre(elfoRequest.getNombre())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> razaService.crearRaza(elfoRequest));
        verify(razaRepository, never()).save(any(Raza.class));
    }

    @Test
    void obtenerRazaPorId_CuandoExiste_DeberiaRetornarRaza() {
        when(razaRepository.findById(1L)).thenReturn(Optional.of(elfoEntity));

        RazaResponse respuesta = razaService.obtenerRazaPorId(1L);

        assertNotNull(respuesta);
        assertEquals(1L, respuesta.getId());
    }

    @Test
    void obtenerRazaPorId_CuandoNoExiste_DeberiaLanzarResourceNotFoundException() {
        when(razaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> razaService.obtenerRazaPorId(99L));
    }

    @Test
    void listarRazas_DeberiaRetornarListaDeRazas() {
        when(razaRepository.findAll()).thenReturn(java.util.List.of(elfoEntity));

        var razas = razaService.listarRazas();

        assertNotNull(razas);
        assertEquals(1, razas.size());
    }

    @Test
    void eliminarRaza_CuandoExiste_DeberiaEliminarExitosamente() {
        when(razaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(razaRepository).deleteById(1L);

        razaService.eliminarRaza(1L);

        verify(razaRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarRaza_CuandoNoExiste_DeberiaLanzarResourceNotFoundException() {
        when(razaRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> razaService.eliminarRaza(99L));
        verify(razaRepository, never()).deleteById(any());
    }

}