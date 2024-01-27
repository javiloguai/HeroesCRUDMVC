package com.w2m.heroestest.restapi.server.mappers;

import com.w2m.heroestest.constants.MapperConstants;
import com.w2m.heroestest.model.dto.SuperHeroDTO;
import com.w2m.heroestest.model.dto.SuperPowerDTO;
import com.w2m.heroestest.model.enums.SuperPower;
import com.w2m.heroestest.restapi.server.requests.HeroRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Imported medical staff licence request mapper.
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface SuperHeroRequestMapper extends RequestMapper<HeroRequest, SuperHeroDTO> {

    SuperHeroRequestMapper INSTANCE = Mappers.getMapper(SuperHeroRequestMapper.class);

    /**
     * Gets mapper.
     *
     * @return the mapper
     */
    static SuperHeroRequestMapper getMapper() {
        return Mappers.getMapper(SuperHeroRequestMapper.class);
    }

    /**
     * From requests to dtos licences.
     *
     * @param requests the requests licences
     * @return the mapped result
     */
    List<SuperHeroDTO> fromRequestsToDtos(List<HeroRequest> requests);

    default SuperPowerDTO map(SuperPower superPower) {
        if (Objects.isNull(superPower)) {
            return null;
        }

        return SuperPowerDTO.builder().superPower(superPower).build();
    }

    default List<SuperPowerDTO> mapList(List<SuperPower> superPower) {
        if (Objects.isNull(superPower)) {
            return null;
        } else if (superPower.isEmpty()) {
            return new ArrayList<SuperPowerDTO>();
        }

        return superPower.stream().map(this::map).toList();
    }

}
