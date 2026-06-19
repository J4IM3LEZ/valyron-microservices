package com.realmofvalyron.ms_misiones.assembler;

import com.realmofvalyron.ms_misiones.controller.MisionController;
import com.realmofvalyron.ms_misiones.dto.MisionResponse;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MisionModelAssembler implements RepresentationModelAssembler<MisionResponse, MisionResponse> {

    @Override
    public MisionResponse toModel(MisionResponse mision) {

        mision.add(linkTo(methodOn(MisionController.class)
                .obtenerMisionPorId(mision.getId())).withSelfRel());

        mision.add(linkTo(methodOn(MisionController.class)
                .listarMisiones(null, null)).withRel("todas-las-misiones"));

        return mision;
    }

}