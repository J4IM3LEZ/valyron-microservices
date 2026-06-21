package com.realmofvalyron.ms_misiones;

import com.realmofvalyron.ms_misiones.client.HistorialClient;
import com.realmofvalyron.ms_misiones.dto.MisionRequest;
import com.realmofvalyron.ms_misiones.dto.MisionResponse;
import com.realmofvalyron.ms_misiones.entity.Mision;
import com.realmofvalyron.ms_misiones.exception.BadRequestException;
import com.realmofvalyron.ms_misiones.exception.ResourceNotFoundException;
import com.realmofvalyron.ms_misiones.repository.MisionRepository;
import com.realmofvalyron.ms_misiones.service.MisionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class MisionServiceTest {

    @Autowired
    private MisionService misionService;

    @MockitoBean
    private MisionRepository misionRepository;

    @MockitoBean
    private HistorialClient historialClient;

    private Mision mision;
    private MisionRequest misionRequest;

    @BeforeEach
    void setUp() {
        mision = Mision.builder()
                .id(1L)
                .nombre("Patrulla de la Comarca")
                .descripcion("Proteger los campos de la Comarca")
                .nivelRequerido(1)
                .recompensaXp(100)
                .recompensaOro(50)
                .dificultad(Mision.Dificultad.FACIL)
                .estadoMision(Mision.EstadoMision.DISPONIBLE)
                .build();

        misionRequest = new MisionRequest();
        misionRequest.setNombre("Patrulla de la Comarca");
        misionRequest.setDescripcion("Proteger los campos de la Comarca");
        misionRequest.setNivelRequerido(1);
        misionRequest.setRecompensaXp(100);
        misionRequest.setRecompensaOro(50);
        misionRequest.setDificultad("FACIL");
    }

    @Test
    void crearMision_Exitoso() {
        when(misionRepository.existsByNombre(misionRequest.getNombre())).thenReturn(false);
        when(misionRepository.save(any(Mision.class))).thenReturn(mision);

        MisionResponse response = misionService.crearMision(misionRequest);

        assertNotNull(response);
        assertEquals("Patrulla de la Comarca", response.getNombre());
        assertEquals("DISPONIBLE", response.getEstadoMision());
        verify(misionRepository, times(1)).save(any(Mision.class));
    }

    @Test
    void crearMision_CuandoYaExisteNombre_DeberiaLanzarBadRequestException() {
        when(misionRepository.existsByNombre(misionRequest.getNombre())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> misionService.crearMision(misionRequest));
        verify(misionRepository, never()).save(any(Mision.class));
    }

    @Test
    void completarMision_Exitoso() {
        when(misionRepository.findById(1L)).thenReturn(Optional.of(mision));
        when(misionRepository.save(any(Mision.class))).thenReturn(mision);
        doNothing().when(historialClient).registrarEvento(
                anyLong(), anyString(), anyString(), anyLong(), anyString());

        MisionResponse response = misionService.completarMision(1L, 1L, "Legolas");

        assertNotNull(response);
        verify(historialClient, times(1)).registrarEvento(
                anyLong(), anyString(), anyString(), anyLong(), anyString());
    }

    @Test
    void completarMision_CuandoYaEstaCompletada_DeberiaLanzarBadRequestException() {
        mision.setEstadoMision(Mision.EstadoMision.COMPLETADA);
        when(misionRepository.findById(1L)).thenReturn(Optional.of(mision));

        assertThrows(BadRequestException.class, () -> misionService.completarMision(1L, 1L, "Legolas"));
        verify(historialClient, never()).registrarEvento(
                anyLong(), anyString(), anyString(), anyLong(), anyString());
    }

    @Test
    void completarMision_CuandoNoExiste_DeberiaLanzarResourceNotFoundException() {
        when(misionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> misionService.completarMision(99L, 1L, "Legolas"));
    }

    @Test
    void eliminarMision_CuandoExiste_DeberiaEliminarExitosamente() {
        when(misionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(misionRepository).deleteById(1L);

        misionService.eliminarMision(1L);

        verify(misionRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarMision_CuandoNoExiste_DeberiaLanzarResourceNotFoundException() {
        when(misionRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> misionService.eliminarMision(99L));
        verify(misionRepository, never()).deleteById(any());
    }

}
