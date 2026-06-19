package com.realmofvalyron.ms_personajes.assembler;

import com.realmofvalyron.ms_personajes.controller.PersonajeController;
import com.realmofvalyron.ms_personajes.dto.PersonajeResponse;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonajeModelAssembler implements RepresentationModelAssembler<PersonajeResponse, PersonajeResponse> {

    @Override
    public PersonajeResponse toModel(PersonajeResponse personaje) {

        personaje.add(linkTo(methodOn(PersonajeController.class)
                .obtenerPersonajePorId(personaje.getId())).withSelfRel());

        personaje.add(linkTo(methodOn(PersonajeController.class)
                .listarPersonajes(null)).withRel("todos-los-personajes"));

        return personaje;
    }

}
