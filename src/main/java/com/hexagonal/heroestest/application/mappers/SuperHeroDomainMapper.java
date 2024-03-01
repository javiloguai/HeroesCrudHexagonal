package com.hexagonal.heroestest.application.mappers;

import com.hexagonal.heroestest.application.models.SuperHeroDTO;
import com.hexagonal.heroestest.application.models.SuperPowerDTO;
import com.hexagonal.heroestest.domain.models.SuperHeroDomain;
import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import com.hexagonal.heroestest.infraestructure.constants.MapperConstants;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SuperHeroDomainMapper extends DomainMapper<SuperHeroDTO, SuperHeroDomain> {

    SuperHeroDomainMapper INSTANCE = Mappers.getMapper(SuperHeroDomainMapper.class);

    default SuperPowerDomain map(SuperPowerDTO superPower) {

        return SuperPowerDomainMapper.INSTANCE.dtoToDomain(superPower);
    }

    default List<SuperPowerDomain> mapDtoList(List<SuperPowerDTO> superPower) {
        return SuperPowerDomainMapper.INSTANCE.dtoToDomain(superPower);
    }

    default SuperPowerDTO map(SuperPowerDomain superPower) {

        return SuperPowerDomainMapper.INSTANCE.domainToDto(superPower);
    }

    default List<SuperPowerDTO> mapList(List<SuperPowerDomain> superPower) {
        return SuperPowerDomainMapper.INSTANCE.domainToDto(superPower);
    }

}
