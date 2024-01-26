package com.w2m.heroestest.core.restapi.services.mappers;

import com.w2m.heroestest.core.constants.MapperConstants;
import com.w2m.heroestest.core.model.domain.SuperPowerDomain;
import com.w2m.heroestest.core.model.dto.SuperPowerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SuperPowerDomainMapper extends DomainMapper<SuperPowerDTO, SuperPowerDomain> {

    SuperPowerDomainMapper INSTANCE = Mappers.getMapper(SuperPowerDomainMapper.class);

}
