package com.w2m.heroestest.restapi.services.impl;

import com.w2m.heroestest.config.exception.AlreadyExistException;
import com.w2m.heroestest.config.exception.BusinessRuleViolatedException;
import com.w2m.heroestest.config.exception.NotFoundException;
import com.w2m.heroestest.model.domain.SuperHeroDomain;
import com.w2m.heroestest.model.dto.SuperHeroDTO;
import com.w2m.heroestest.model.enums.SuperPower;
import com.w2m.heroestest.restapi.persistence.entities.HeroSuperPowerEntity;
import com.w2m.heroestest.restapi.persistence.entities.SuperHeroEntity;
import com.w2m.heroestest.restapi.persistence.mappers.SuperHeroDataBaseMapper;
import com.w2m.heroestest.restapi.persistence.repositories.HeroSuperPowerRepository;
import com.w2m.heroestest.restapi.persistence.repositories.SuperHeroRepository;
import com.w2m.heroestest.restapi.services.BasicService;
import com.w2m.heroestest.restapi.services.SuperHeroesService;
import com.w2m.heroestest.restapi.services.mappers.SuperHeroDomainMapper;
import com.w2m.heroestest.utils.ParamUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
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
    @Cacheable(cacheNames = "allheroes")
    public List<SuperHeroDomain> getAllSuperHeroes() {

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroRepository.findAll());
    }

    @Override
    @Cacheable(cacheNames = "pagedallheroes")
    public Page<SuperHeroDomain> getAllSuperHeroes(Pageable pageable) {
        Page<SuperHeroEntity> pagedResults = superHeroRepository.findAll(pageable);

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(pagedResults);
    }

    @Override
    @Cacheable(cacheNames = "heroes", key = "#name")
    public List<SuperHeroDomain> getAllSuperHeroesByName(@NotNull final String name) {
        if (ParamUtils.paramNotInformed(name)) {
            throw new BusinessRuleViolatedException("name field is Mandatory");
        }

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroRepository.findByNameContaining(name));
    }

    @Override
    @Cacheable(cacheNames = "pagedheroes", key = "#name")
    public Page<SuperHeroDomain> getAllSuperHeroesByName(@NotNull String name, Pageable pageable) {
        if (ParamUtils.paramNotInformed(name)) {
            throw new BusinessRuleViolatedException("name field is Mandatory");
        }

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(
                superHeroRepository.findByNameContainingIgnoreCase(name, pageable));
    }

    @Override
    @Cacheable(cacheNames = "powers", key = "#power")
    public List<SuperHeroDomain> getAllSuperHeroesBySuperPower(@NotNull final SuperPower power) {
        if (power == null) {
            throw new BusinessRuleViolatedException("power field is Mandatory");

        }
        List<HeroSuperPowerEntity> powerList = heroSuperPowerRepository.findBySuperPower(power);
        List<Long> heroesIds = powerList.stream().map(p -> p.getSuperheroId()).distinct().toList();

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroRepository.findAllById(heroesIds));
    }

    @Override
    @Cacheable(cacheNames = "hero", key = "#id")
    public SuperHeroDomain findById(@NotNull final Long id) {
        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(getEntityById(id));
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "pagedheroes", allEntries = true), @CacheEvict(value = "allheroes", allEntries = true),
            @CacheEvict(value = "pagedallheroes", allEntries = true),
            @CacheEvict(value = "powers", allEntries = true) })
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
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "pagedheroes", allEntries = true), @CacheEvict(value = "allheroes", allEntries = true),
            @CacheEvict(value = "pagedallheroes", allEntries = true),
            @CacheEvict(value = "powers", allEntries = true) })
    public SuperHeroDomain updateSuperHero(@NotNull Long id, @NotNull SuperHeroDTO superHeroDTO) {

        this.checkIfHeroAlreadyExists(id, superHeroDTO);
        final SuperHeroDomain heroDO = this.validateHeroData(superHeroDTO);

        SuperHeroEntity heroToUpdate = this.getEntityById(id);
        SuperHeroDataBaseMapper.INSTANCE.copyToEntity(heroDO, heroToUpdate);
        List<HeroSuperPowerEntity> superPowers = new ArrayList<>();
        superPowers.addAll(heroToUpdate.getSuperPower());

        superHeroRepository.saveAndFlush(heroToUpdate);

        heroSuperPowerRepository.deleteAllBySuperheroId(id);
        heroSuperPowerRepository.flush();

        superPowers.forEach(h -> {
            h.setSuperheroId(id);

        });
        heroSuperPowerRepository.saveAllAndFlush(superPowers);

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(this.getEntityById(id));
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "pagedheroes", allEntries = true), @CacheEvict(value = "allheroes", allEntries = true),
            @CacheEvict(value = "pagedallheroes", allEntries = true),
            @CacheEvict(value = "powers", allEntries = true) })
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
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "allheroes", allEntries = true), @CacheEvict(value = "powers", allEntries = true) })
    public void deleteSuperHeroById(long id) {

        heroSuperPowerRepository.deleteAllBySuperheroId(id);
        heroSuperPowerRepository.flush();

        superHeroRepository.deleteById(id);
        superHeroRepository.flush();

    }

    @Override
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "allheroes", allEntries = true), @CacheEvict(value = "powers", allEntries = true) })
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
