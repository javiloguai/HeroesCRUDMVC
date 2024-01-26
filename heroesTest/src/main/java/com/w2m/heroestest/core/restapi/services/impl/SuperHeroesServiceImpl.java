package com.w2m.heroestest.core.restapi.services.impl;

import com.w2m.heroestest.core.config.exception.AlreadyExistException;
import com.w2m.heroestest.core.config.exception.BusinessRuleViolatedException;
import com.w2m.heroestest.core.config.exception.NotFoundException;
import com.w2m.heroestest.core.model.domain.SuperHeroDomain;
import com.w2m.heroestest.core.model.dto.SuperHeroDTO;
import com.w2m.heroestest.core.model.enums.SuperPower;
import com.w2m.heroestest.core.model.persistence.entities.HeroSuperPowerEntity;
import com.w2m.heroestest.core.model.persistence.entities.SuperHeroEntity;
import com.w2m.heroestest.core.model.persistence.mappers.SuperHeroDataBaseMapper;
import com.w2m.heroestest.core.model.persistence.repositories.HeroSuperPowerRepository;
import com.w2m.heroestest.core.model.persistence.repositories.SuperHeroRepository;
import com.w2m.heroestest.core.restapi.services.BasicService;
import com.w2m.heroestest.core.restapi.services.SuperHeroesService;
import com.w2m.heroestest.core.restapi.services.mappers.SuperHeroDomainMapper;
import com.w2m.heroestest.core.utils.ParamUtils;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author jruizh
 * I put some examples of other kind of call I will not implement anything on other examples
 */
@Service
@Validated
@Transactional
public class SuperHeroesServiceImpl extends BasicService implements SuperHeroesService {

    @Autowired
    private SuperHeroRepository superHeroRepository;

    @Autowired
    private HeroSuperPowerRepository heroSuperPowerRepository;

    @Override
    public List<SuperHeroDomain> getAllSSuperHeroes() {

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroRepository.findAll());
    }

    @Override
    public List<SuperHeroDomain> getAllSSuperHeroesByName(@NotNull final String name) {
        if (ParamUtils.paramNotInformed(name)) {
            throw new BusinessRuleViolatedException("name field is Mandatory");
        }

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(
                superHeroRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public List<SuperHeroDomain> getAllSSuperHeroesBySuperPower(@NotNull final SuperPower power) {
        if (power == null) {
            throw new BusinessRuleViolatedException("power field is Mandatory");

        }
        List<HeroSuperPowerEntity> powerList = heroSuperPowerRepository.findBySuperPower(power);
        List<Long> heroesIds = powerList.stream().map(p -> p.getSuperheroId()).distinct().toList();

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroRepository.findAllById(heroesIds));
    }

    @Override
    public SuperHeroDomain findById(@NotNull final Long id) {
        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(getEntityById(id));
    }

    @Override
    public SuperHeroDomain createSuperHero(final SuperHeroDTO superHeroDTO) {

        this.checkIfHeroAlreadyExists(superHeroDTO);
        final SuperHeroDomain heroDO = this.validateHeroData(superHeroDTO);

        SuperHeroEntity heroE = SuperHeroDataBaseMapper.INSTANCE.domainToEntity(heroDO);

        superHeroRepository.saveAndFlush(heroE);

        final Long heroId = heroE.getId();

        List<HeroSuperPowerEntity> superPowers = heroE.getSuperPower();

        superPowers.forEach(h -> {
            h.setSuperheroId(heroId);

        });
        heroSuperPowerRepository.saveAllAndFlush(superPowers);

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(this.getEntityById(heroId));
    }

    @Override
    public SuperHeroDomain updateSuperHero(@NotNull Long id, @NotNull SuperHeroDTO superHeroDTO) {

        this.checkIfHeroAlreadyExists(id, superHeroDTO);
        final SuperHeroDomain heroDO = this.validateHeroData(superHeroDTO);

        SuperHeroEntity heroToUpdate = this.getEntityById(id);
        SuperHeroDataBaseMapper.INSTANCE.copyToEntity(heroDO, heroToUpdate);

        List<HeroSuperPowerEntity> superPowers = heroToUpdate.getSuperPower();

        heroSuperPowerRepository.deleteAllBySuperheroId(id);
        heroSuperPowerRepository.flush();

        superPowers.forEach(h -> {
            h.setSuperheroId(id);

        });
        heroSuperPowerRepository.saveAllAndFlush(superPowers);

        superHeroRepository.saveAndFlush(heroToUpdate);

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(this.getEntityById(id));
    }

    @Override
    public SuperHeroDomain addSuperPower(@NotNull Long id, @NotNull SuperPower power) {
        if (id == null) {
            throw new BusinessRuleViolatedException("Id field is Mandatory");
        }
        if (power == null) {
            throw new BusinessRuleViolatedException("power field is Mandatory");
        }

        HeroSuperPowerEntity powerToAdd = HeroSuperPowerEntity.builder().superheroId(id).superPower(power).build();

        heroSuperPowerRepository.saveAndFlush(powerToAdd);

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(this.getEntityById(id));
    }

    @Override
    public void deleteSuperHeroById(long id) {

        heroSuperPowerRepository.deleteAllBySuperheroId(id);
        heroSuperPowerRepository.flush();

        superHeroRepository.deleteById(id);
        superHeroRepository.flush();

    }

    @Override
    public void deleteAllSuperHeros() {
        heroSuperPowerRepository.deleteAll();
        heroSuperPowerRepository.flush();
        superHeroRepository.deleteAll();
        superHeroRepository.flush();

    }

    private SuperHeroEntity getEntityById(@NotNull final Long id) {
        return superHeroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Not found hero with id %s", id)));
    }

    private void checkIfHeroAlreadyExists(final SuperHeroDTO superHeroDTO) {
        superHeroRepository.findFirstByNameIgnoreCase(superHeroDTO.getName())
                .ifPresent(this::throwAlreadyExistException);
    }

    private void checkIfHeroAlreadyExists(@NotNull final Long id, final SuperHeroDTO superHeroDTO) {
        superHeroRepository.findFirstByNameIgnoreCase(superHeroDTO.getName()).ifPresent(h -> {
            if (h.getId() != id.longValue()) {
                throwAlreadyExistException(h);
            }
        });
    }

    private void throwAlreadyExistException(SuperHeroEntity superHeroEntity) {
        throw new AlreadyExistException("This Hero already exists: " + superHeroEntity.toString());
    }

    private SuperHeroDomain validateHeroData(final SuperHeroDTO superHeroDTO) {

        if (ParamUtils.paramNotInformed(superHeroDTO.getName())) {
            throw new BusinessRuleViolatedException("Hero name cannot be empty");
        }
        if (ParamUtils.paramNotInformed(superHeroDTO.getSuperPower())) {
            throw new BusinessRuleViolatedException("Hero superpowers list cannot be empty");
        }

        return SuperHeroDomainMapper.INSTANCE.dtoToDomain(superHeroDTO);
    }

}
