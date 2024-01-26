package com.w2m.heroestest.core.restapi.services.mappers;

import com.w2m.heroestest.core.constants.MapperConstants;
import com.w2m.heroestest.core.model.domain.SuperHeroDomain;
import com.w2m.heroestest.core.model.domain.SuperPowerDomain;
import com.w2m.heroestest.core.model.dto.SuperHeroDTO;
import com.w2m.heroestest.core.model.dto.SuperPowerDTO;
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
