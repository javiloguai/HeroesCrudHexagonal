package com.hexagonal.heroestest.infraestructure.db.mappers;

import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import com.hexagonal.heroestest.infraestructure.constants.MapperConstants;
import com.hexagonal.heroestest.infraestructure.db.entities.HeroSuperPowerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The Interface SuperPowerDataBaseMapper.
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SuperPowerDataBaseMapper extends DatabaseMapper<SuperPowerDomain, HeroSuperPowerEntity> {

    /**
     * Gets the mapper.
     *
     * @return the mapper
     */
    SuperPowerDataBaseMapper INSTANCE = Mappers.getMapper(SuperPowerDataBaseMapper.class);

}
