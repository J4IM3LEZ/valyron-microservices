package com.realmofvalyron.ms_razas;

import com.realmofvalyron.ms_razas.dto.RazaResponse;
import com.realmofvalyron.ms_razas.entity.Raza;
import com.realmofvalyron.ms_razas.repository.RazaRepository;
import com.realmofvalyron.ms_razas.service.RazaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RazaServiceTest {

    @Mock
    private RazaRepository razaRepository;

    @InjectMocks
    private RazaService razaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cuandoObtenerRazaPorId_entoncesRetornaRazaResponse() {
        // 1. Configuración del Mock (when...thenReturn)
        Raza razaSimulada = Raza.builder()
                .id(1L)
                .nombre("Elfo")
                .descripcion("Alta destreza y magia")
                .build();
        when(razaRepository.findById(1L)).thenReturn(Optional.of(razaSimulada));

        // 2. Llamada al Método del Servicio
        RazaResponse resultado = razaService.obtenerRazaPorId(1L);

        // 3. Verificación
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Elfo", resultado.getNombre());
        verify(razaRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        // 1. Configuración del Mock (GIVEN)
        Raza razaSimulada = Raza.builder()
                .id(1L)
                .nombre("Enano")
                .descripcion("Gran resistencia")
                .build();
        // Simulamos que el repositorio devuelve una lista con nuestra raza
        when(razaRepository.findAll()).thenReturn(List.of(razaSimulada));

        // 2. Llamada al Método del Servicio (WHEN)
        List<RazaResponse> razas = razaService.listarRazas();

        // 3. Verificación de la Lista Devuelta (THEN) - Siguiendo tal cual la guía del profe
        assertNotNull(razas);
        assertEquals(1, razas.size());
        assertEquals("Enano", razas.get(0).getNombre());
        verify(razaRepository, times(1)).findAll();
    }
}
