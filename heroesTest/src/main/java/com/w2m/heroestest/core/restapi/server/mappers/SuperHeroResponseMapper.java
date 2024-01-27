package com.w2m.heroestest.core.restapi.server.mappers;

import com.w2m.heroestest.core.constants.MapperConstants;
import com.w2m.heroestest.core.model.domain.SuperHeroDomain;
import com.w2m.heroestest.core.model.domain.SuperPowerDomain;
import com.w2m.heroestest.core.model.enums.SuperPower;
import com.w2m.heroestest.core.restapi.server.responses.HeroResponse;
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
