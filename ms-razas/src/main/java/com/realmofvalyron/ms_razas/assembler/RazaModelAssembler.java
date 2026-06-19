package com.realmofvalyron.ms_razas.assembler;

import com.realmofvalyron.ms_razas.controller.RazaController;
import com.realmofvalyron.ms_razas.dto.RazaResponse;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RazaModelAssembler implements RepresentationModelAssembler<RazaResponse, RazaResponse> {

    @Override
    public RazaResponse toModel(RazaResponse raza) {

        raza.add(linkTo(methodOn(RazaController.class)
                .obtenerRazaPorId(raza.getId())).withSelfRel());

        raza.add(linkTo(methodOn(RazaController.class)
                .listarRazas(null)).withRel("todas-las-razas"));

        return raza;
    }

}
