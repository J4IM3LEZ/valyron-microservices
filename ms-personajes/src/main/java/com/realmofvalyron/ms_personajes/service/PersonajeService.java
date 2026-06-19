package com.realmofvalyron.ms_personajes.service;

import com.realmofvalyron.ms_personajes.client.RazaClient;
import com.realmofvalyron.ms_personajes.dto.PersonajeRequest;
import com.realmofvalyron.ms_personajes.dto.PersonajeResponse;
import com.realmofvalyron.ms_personajes.dto.RazaDTO;
import com.realmofvalyron.ms_personajes.entity.Personaje;
import com.realmofvalyron.ms_personajes.repository.PersonajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonajeService {

    private final PersonajeRepository personajeRepository;
    private final RazaClient razaClient;

    public PersonajeResponse crearPersonaje(PersonajeRequest request) {

        if (personajeRepository.existsByNombre(request.getNombre())) {
                throw new com.realmofvalyron.ms_personajes.exception.BadRequestException("Ya existe un personaje con ese nombre");
        }

        // Consultar ms-razas para obtener los bonos raciales
        RazaDTO raza = razaClient.obtenerRazaPorId(request.getRazaId());

        if (raza == null) {
                throw new com.realmofvalyron.ms_personajes.exception.ResourceNotFoundException("Raza no encontrada con id: " + request.getRazaId());
        }

        Personaje personaje = Personaje.builder()
                .nombre(request.getNombre())
                .razaId(request.getRazaId())
                .nivel(1)
                .experiencia(0)
                .fuerza(10 + raza.getBonusFuerza())
                .destreza(10 + raza.getBonusDestreza())
                .sabiduria(10 + raza.getBonusSabiduria())
                .vitalidad(10 + raza.getBonusVitalidad())
                .oro(100)
                .estado(Personaje.Estado.ACTIVO)
                .build();

        personajeRepository.save(personaje);

        return mapToResponse(personaje, raza);
    }

    public List<PersonajeResponse> listarPersonajes() {
        return personajeRepository.findAll()
                .stream()
                .map(p -> mapToResponse(p, razaClient.obtenerRazaPorId(p.getRazaId())))
                .collect(Collectors.toList());
    }

    public PersonajeResponse obtenerPersonajePorId(Long id) {
        Personaje personaje = personajeRepository.findById(id)
                    .orElseThrow(() -> new com.realmofvalyron.ms_personajes.exception.ResourceNotFoundException("Personaje no encontrado con id: " + id));
        RazaDTO raza = razaClient.obtenerRazaPorId(personaje.getRazaId());
        return mapToResponse(personaje, raza);
    }

    public PersonajeResponse obtenerPersonajePorNombre(String nombre) {
        Personaje personaje = personajeRepository.findByNombre(nombre)
                .orElseThrow(() -> new com.realmofvalyron.ms_personajes.exception.ResourceNotFoundException("Personaje no encontrado con nombre: " + nombre));
        RazaDTO raza = razaClient.obtenerRazaPorId(personaje.getRazaId());
        return mapToResponse(personaje, raza);
    }

    public void eliminarPersonaje(Long id) {
        if (!personajeRepository.existsById(id)) {
                throw new com.realmofvalyron.ms_personajes.exception.ResourceNotFoundException("Personaje no encontrado con id: " + id);
        }
        personajeRepository.deleteById(id);
    }

        public PersonajeResponse actualizarPersonaje(Long id, PersonajeRequest request) {
            Personaje personaje = personajeRepository.findById(id)
                    .orElseThrow(() -> new com.realmofvalyron.ms_personajes.exception.ResourceNotFoundException("Personaje no encontrado con id: " + id));

            RazaDTO raza = razaClient.obtenerRazaPorId(request.getRazaId());
            if (raza == null) {
                throw new com.realmofvalyron.ms_personajes.exception.ResourceNotFoundException("Raza no encontrada con id: " + request.getRazaId());
            }

            personaje.setNombre(request.getNombre());
            personaje.setRazaId(request.getRazaId());
            // recalculate stats based on raza
            personaje.setFuerza(10 + raza.getBonusFuerza());
            personaje.setDestreza(10 + raza.getBonusDestreza());
            personaje.setSabiduria(10 + raza.getBonusSabiduria());
            personaje.setVitalidad(10 + raza.getBonusVitalidad());

            personajeRepository.save(personaje);
            return mapToResponse(personaje, raza);
        }

        private PersonajeResponse mapToResponse(Personaje personaje, RazaDTO raza) {
            return PersonajeResponse.builder()
                    .id(personaje.getId())
                    .nombre(personaje.getNombre())
                    .razaId(personaje.getRazaId())
                    .razaNombre(raza != null ? raza.getNombre() : "Desconocida")
                    .nivel(personaje.getNivel())
                    .experiencia(personaje.getExperiencia())
                    .fuerza(personaje.getFuerza())
                    .destreza(personaje.getDestreza())
                    .sabiduria(personaje.getSabiduria())
                    .vitalidad(personaje.getVitalidad())
                    .oro(personaje.getOro())
                    .estado(personaje.getEstado().name())
                    .build();
        }

}
