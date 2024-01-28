package com.w2m.heroestest.restapi.server.assemblers;


import com.w2m.heroestest.model.domain.SuperHeroDomain;
import com.w2m.heroestest.model.domain.SuperPowerDomain;
import com.w2m.heroestest.model.enums.SuperPower;
import com.w2m.heroestest.restapi.server.controllers.SuperHeroeController;
import com.w2m.heroestest.restapi.server.responses.HeroResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilderFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HeroPageModelAssembler
        extends RepresentationModelAssemblerSupport<SuperHeroDomain, HeroResponse> {

    public HeroPageModelAssembler() {
        super(SuperHeroeController.class, HeroResponse.class);
    }

    @Override
    public HeroResponse toModel(SuperHeroDomain entity)
    {
        HeroResponse albumModel = instantiateModel(entity);

        albumModel.setId(String.valueOf(entity.getId()));
        albumModel.setName(entity.getName());
        albumModel.setDescription(entity.getDescription());
        albumModel.setSuperPower(toActorModel(entity.getSuperPower()));
        return albumModel;
    }

    @Override
    public CollectionModel<HeroResponse> toCollectionModel(Iterable<? extends SuperHeroDomain> entities)
    {
        CollectionModel<HeroResponse> actorModels = super.toCollectionModel(entities);


        return actorModels;
    }

    private List<SuperPower> toActorModel(List<SuperPowerDomain> powers) {
        if (powers.isEmpty())
            return Collections.emptyList();

        return powers.stream()
                .map(actor -> actor.getSuperPower())
                .collect(Collectors.toList());
    }
}