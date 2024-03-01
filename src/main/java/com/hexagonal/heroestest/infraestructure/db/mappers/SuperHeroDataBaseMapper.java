package com.hexagonal.heroestest.infraestructure.db.mappers;

import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import com.hexagonal.heroestest.infraestructure.constants.MapperConstants;
import com.hexagonal.heroestest.infraestructure.db.entities.HeroSuperPowerEntity;
import com.hexagonal.heroestest.infraestructure.db.entities.SuperHeroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * The Interface SuperHeroDataBaseMapper.
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SuperHeroDataBaseMapper extends DatabaseMapper<SuperHeroDomain, SuperHeroEntity> {

    /**
     * Gets the mapper.
     *
     * @return the mapper
     */
    SuperHeroDataBaseMapper INSTANCE = Mappers.getMapper(SuperHeroDataBaseMapper.class);

    default SuperPowerDomain map(HeroSuperPowerEntity superPower) {

        return SuperPowerDataBaseMapper.INSTANCE.entityToDomain(superPower);
    }

    default List<SuperPowerDomain> mapList(List<HeroSuperPowerEntity> superPower) {
        return SuperPowerDataBaseMapper.INSTANCE.entityToDomain(superPower);
    }

    @Mapping(target = "id", ignore = true)
    void copyToEntity(SuperHeroDomain domain, @MappingTarget SuperHeroEntity entity);

}
