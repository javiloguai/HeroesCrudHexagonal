package com.hexagonal.heroestest.infraestructure.restapi.assemblers;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import com.hexagonal.heroestest.infraestructure.restapi.controllers.SuperHeroeController;
import com.hexagonal.heroestest.infraestructure.restapi.model.responses.HeroResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class HeroPageModelAssembler extends RepresentationModelAssemblerSupport<SuperHeroDomain, HeroResponse> {

    public HeroPageModelAssembler() {
        super(SuperHeroeController.class, HeroResponse.class);
    }

    @Override
    public HeroResponse toModel(SuperHeroDomain entity) {
        HeroResponse heroModel = instantiateModel(entity);

        heroModel.setId(String.valueOf(entity.getId()));
        heroModel.setName(entity.getName());
        heroModel.setDescription(entity.getDescription());
        heroModel.setSuperPower(toHeroModel(entity.getSuperPower()));
        return heroModel;
    }

    @Override
    public CollectionModel<HeroResponse> toCollectionModel(Iterable<? extends SuperHeroDomain> entities) {

        return super.toCollectionModel(entities);
    }

    private List<SuperPower> toHeroModel(List<SuperPowerDomain> powers) {
        if (powers.isEmpty())
            return Collections.emptyList();

        return powers.stream().map(hero -> hero.getSuperPower()).toList();
    }
}