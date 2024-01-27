package com.w2m.heroestest.restapi.persistence.mappers;

import com.w2m.heroestest.constants.MapperConstants;
import com.w2m.heroestest.model.domain.SuperHeroDomain;
import com.w2m.heroestest.model.domain.SuperPowerDomain;
import com.w2m.heroestest.restapi.persistence.entities.HeroSuperPowerEntity;
import com.w2m.heroestest.restapi.persistence.entities.SuperHeroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * The Interface LicenceMapper.
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
