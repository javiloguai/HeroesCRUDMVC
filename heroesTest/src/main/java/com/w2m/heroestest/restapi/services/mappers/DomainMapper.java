package com.w2m.heroestest.restapi.services.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

/**
 * Base Mapper for Domain services.
 * Responsible for the transformation between Data Transfer Objects used in domain model
 * objects
 * Data transfer objects are those objects which represents Domain service communications data structures.
 * Only defines particular methods to make clear which type of beans are involved on transformations, making use of
 * BaseMapper methods.
 */
public interface DomainMapper<DTO, DOMAIN> {
    /**
     * Map data transfer object on a domain model. Both contained in Optional container.
     *
     * @param optionalA Optional container containing data transfer object
     * @return Optional container containing domain model object.
     */
    default Optional<DOMAIN> dtoToDomain(Optional<DTO> optionalA) {
        return optionalA.map(this::dtoToDomain);
    }

    /**
     * Map domain model object on a data transfer object. Both contained in Optional container.
     *
     * @param optionalB Optional container containing domain model object
     * @return Optional container containing data transfer object.
     */
    default Optional<DTO> domainToDto(Optional<DOMAIN> optionalB) {
        return optionalB.map(this::domainToDto);
    }

    /**
     * Map data transfer object on a domain model object. Both contained in Optional container.
     *
     * @param dto entity model model object.
     * @return domain model object.
     */
    DOMAIN dtoToDomain(DTO dto);

    /**
     * Map domain model object on a data transfer object.
     *
     * @param domain domain model object
     * @return data transfer object.
     */
    DTO domainToDto(DOMAIN domain);

    /**
     * Map list of data transfer objects on a list of domain model objects. Both contained in Page container.
     *
     * @param dtoPage Page container containing data transfer objects
     * @return Page container containing domain model objects
     */
    default Page<DOMAIN> dtoToDomain(Page<DTO> dtoPage) {
        if (dtoPage == null || dtoPage.isEmpty()) {
            return Page.empty();
        } else {
            List<DOMAIN> list = dtoPage.stream().map(this::dtoToDomain).toList();
            return new PageImpl<>(list);
        }
    }

    /**
     * Map list of domain model objects on a list of data transfer objects. Both contained in Page container.
     *
     * @param domainPage Page container containing domain model objects
     * @return Page container containing data transfer objects.
     */
    default Page<DTO> domainToDto(Page<DOMAIN> domainPage) {
        if (domainPage == null || domainPage.isEmpty()) {
            return Page.empty();
        } else {
            List<DTO> list = domainPage.stream().map(this::domainToDto).toList();
            return new PageImpl<>(list);
        }
    }

    /**
     * Map list of domain model objects  on a list of domain model objects
     *
     * @param domainList List containing data transfer object
     * @return List containing domain model object.
     */
    default List<DTO> domainToDto(List<DOMAIN> domainList) {
        if (domainList == null || domainList.isEmpty()) {
            return List.of();
        } else {
            return domainList.stream().map(this::domainToDto).toList();
        }
    }

    /**
     * Map list of data transfer objects on a list of domain model objects
     *
     * @param dtoList List containing data transfer objects
     * @return List containing domain model objects
     */
    default List<DOMAIN> dtoToDomain(List<DTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        } else {
            return dtoList.stream().map(this::dtoToDomain).toList();
        }
    }

    /**
     * Map Iterable of domain model objects on a List of domain model objects.
     *
     * @param domainList iterable containing data transfer objects
     * @return List containing domain model objects
     */
    Iterable<DTO> domainToDto(Iterable<DOMAIN> domainList);

    /**
     * Map Iterable of data transfer objects on a List of domain model objects.
     *
     * @param dtoList iterable containing data transfer objects
     * @return List containing domain model objects
     */
    List<DOMAIN> dtoToDomain(Iterable<DTO> dtoList);

    /**
     * Updates domain model objects with data in data transfer object
     * Null values at source are ignored in update
     *
     * @param dtoSource    source data transfer object
     * @param domainTarget target domain model object
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDomainWithDto(DTO dtoSource, @MappingTarget DOMAIN domainTarget);

    /**
     * Updates data transfer object with data in domain model object
     * Null values at source are ignored in update
     *
     * @param domainSource source domain model object
     * @param dtoTarget    target data transfer object
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDtoWithDomain(DOMAIN domainSource, @MappingTarget DTO dtoTarget);

}
