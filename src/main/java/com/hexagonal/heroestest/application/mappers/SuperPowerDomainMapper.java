package com.hexagonal.heroestest.application.mappers;

import com.hexagonal.heroestest.application.models.SuperPowerDTO;
import com.hexagonal.heroestest.domain.models.SuperPowerDomain;
import com.hexagonal.heroestest.infraestructure.constants.MapperConstants;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SuperPowerDomainMapper extends DomainMapper<SuperPowerDTO, SuperPowerDomain> {

    SuperPowerDomainMapper INSTANCE = Mappers.getMapper(SuperPowerDomainMapper.class);

}
