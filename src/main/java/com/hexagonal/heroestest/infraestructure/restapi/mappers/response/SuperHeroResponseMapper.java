package com.hexagonal.heroestest.infraestructure.restapi.mappers.response;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import com.hexagonal.heroestest.infraestructure.constants.MapperConstants;
import com.hexagonal.heroestest.infraestructure.restapi.model.responses.HeroResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

/**
 * The SuperHeroResponseMapper
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SuperHeroResponseMapper extends ResponseMapper<SuperHeroDomain, HeroResponse> {

    SuperHeroResponseMapper INSTANCE = Mappers.getMapper(SuperHeroResponseMapper.class);

    default SuperPower map(SuperPowerDomain superPowerDomain) {
        if (Objects.isNull(superPowerDomain)) {
            return null;
        }

        return superPowerDomain.getSuperPower();
    }

}
