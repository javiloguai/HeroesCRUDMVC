package com.w2m.heroestest.core.restapi.persistence.mappers;

import com.w2m.heroestest.core.constants.MapperConstants;
import com.w2m.heroestest.core.model.domain.SuperPowerDomain;
import com.w2m.heroestest.core.restapi.persistence.entities.HeroSuperPowerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The Interface LicenceMapper.
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
