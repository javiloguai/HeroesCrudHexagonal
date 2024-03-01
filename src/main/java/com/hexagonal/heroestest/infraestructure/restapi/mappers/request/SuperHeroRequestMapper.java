package com.hexagonal.heroestest.infraestructure.restapi.mappers.request;

import com.hexagonal.heroestest.application.models.SuperHeroDTO;
import com.hexagonal.heroestest.application.models.SuperPowerDTO;
import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.infraestructure.constants.MapperConstants;
import com.hexagonal.heroestest.infraestructure.restapi.model.requests.HeroRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The SuperHero request mapper.
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SuperHeroRequestMapper extends RequestMapper<HeroRequest, SuperHeroDTO> {

    SuperHeroRequestMapper INSTANCE = Mappers.getMapper(SuperHeroRequestMapper.class);

    /**
     * Gets mapper.
     *
     * @return the mapper
     */
    static SuperHeroRequestMapper getMapper() {
        return Mappers.getMapper(SuperHeroRequestMapper.class);
    }

    /**
     * From requests to dtos licences.
     *
     * @param requests the requests licences
     * @return the mapped result
     */
    List<SuperHeroDTO> fromRequestsToDtos(List<HeroRequest> requests);

    default SuperPowerDTO map(SuperPower superPower) {
        if (Objects.isNull(superPower)) {
            return null;
        }

        return SuperPowerDTO.builder().superPower(superPower).build();
    }

    default List<SuperPowerDTO> mapList(List<SuperPower> superPower) {
        if (Objects.isNull(superPower)) {
            return Collections.emptyList();
        } else if (superPower.isEmpty()) {
            return new ArrayList<SuperPowerDTO>();
        }

        return superPower.stream().map(this::map).toList();
    }

}
