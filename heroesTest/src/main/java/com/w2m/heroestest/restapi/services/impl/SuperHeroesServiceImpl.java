package com.w2m.heroestest.restapi.services.impl;

import com.w2m.heroestest.config.exception.AlreadyExistException;
import com.w2m.heroestest.config.exception.BusinessRuleViolatedException;
import com.w2m.heroestest.config.exception.NotFoundException;
import com.w2m.heroestest.model.domain.SuperHeroDomain;
import com.w2m.heroestest.model.domain.SuperPowerDomain;
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
import java.util.Objects;

/**
 * @author jruizh
 * I put some examples of other kind of call I will not implement anything on other examples
 */
@Service
@Validated
@Transactional
public class SuperHeroesServiceImpl extends BasicService implements SuperHeroesService {

    private final static String ID_MANDATORY="Id field is Mandatory";
    private final static String NAME_EMPTY="Hero name cannot be empty";
    private final static String POWERS_EMPTY="Hero superpowers list cannot be empty";
    private final static String PAGE_MANDATORY="page info is Mandatory";
    private final static String NAME_MANDATORY="name field is Mandatory";
    private final static String POWER_MANDATORY="power field is Mandatory";
    private final static String HERO_MANDATORY="The hero Object is Mandatory";

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
    public Page<SuperHeroDomain> pageAllSuperHeroes( final Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new BusinessRuleViolatedException(PAGE_MANDATORY);
        }
        Page<SuperHeroEntity> pagedResults = superHeroRepository.findAll(pageable);

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(pagedResults);
    }

    @Override
    @Cacheable(cacheNames = "heroes", key = "#name")
    public List<SuperHeroDomain> getAllSuperHeroesByName( final String name) {
        if (ParamUtils.paramNotInformed(name)) {
            throw new BusinessRuleViolatedException(NAME_MANDATORY);
        }

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroRepository.findByNameContaining(name));
    }

    @Override
    @Cacheable(cacheNames = "pagedheroes", key = "#name")
    public Page<SuperHeroDomain> pageAllSuperHeroesByName( final String name,  final Pageable pageable) {
        if (ParamUtils.paramNotInformed(name)) {
            throw new BusinessRuleViolatedException(NAME_MANDATORY);
        }
        if (Objects.isNull(pageable)) {
            throw new BusinessRuleViolatedException(PAGE_MANDATORY);
        }

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(
                superHeroRepository.findByNameContainingIgnoreCase(name, pageable));
    }

    @Override
    @Cacheable(cacheNames = "powers", key = "#power")
    public List<SuperHeroDomain> getAllSuperHeroesBySuperPower( final SuperPower power) {
        if (power == null) {
            throw new BusinessRuleViolatedException(POWER_MANDATORY);

        }
        List<HeroSuperPowerEntity> powerList = heroSuperPowerRepository.findBySuperPower(power);
        List<Long> heroesIds = powerList.stream().map(p -> p.getSuperheroId()).distinct().toList();

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(superHeroRepository.findAllById(heroesIds));
    }

    @Override
    @Cacheable(cacheNames = "hero", key = "#id")
    public SuperHeroDomain findById( final Long id) {
        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(getEntityById(id));
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "pagedheroes", allEntries = true), @CacheEvict(value = "allheroes", allEntries = true),
            @CacheEvict(value = "pagedallheroes", allEntries = true),
            @CacheEvict(value = "powers", allEntries = true) })
    public SuperHeroDomain createSuperHero(final SuperHeroDTO superHeroDTO) {
        SuperHeroDomain heroDO = this.validateHeroToCreate(superHeroDTO);

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
    public SuperHeroDomain updateSuperHero(final Long id, final SuperHeroDTO superHeroDTO) {

        SuperHeroDomain heroDO = this.validateHeroToUpdate(id, superHeroDTO);

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
    public SuperHeroDomain addSuperPower(final Long id, final SuperPower power) {

        validateAddPower(id,power);

        HeroSuperPowerEntity powerToAdd = HeroSuperPowerEntity.builder().superheroId(id).superPower(power).build();

        heroSuperPowerRepository.saveAndFlush(powerToAdd);

        return SuperHeroDataBaseMapper.INSTANCE.entityToDomain(this.getEntityById(id));
    }

    private void validateAddPower(final Long id,final  SuperPower power){

        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        if (power == null) {
            throw new BusinessRuleViolatedException(POWER_MANDATORY);
        }

        SuperHeroDomain hiro = this.findById(id);

        List<SuperPowerDomain> powerList = hiro.getSuperPower();
        List<SuperPower> hiroPowers = powerList.stream().map(p -> p.getSuperPower()).distinct().toList();

        if(!hiroPowers.isEmpty() && hiroPowers.contains(power)){
            throw new AlreadyExistException("This Hero already owns that superpower: " + power.toString());
        }
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "pagedheroes", allEntries = true), @CacheEvict(value = "allheroes", allEntries = true),
            @CacheEvict(value = "pagedallheroes", allEntries = true),
            @CacheEvict(value = "powers", allEntries = true) })
    public void deleteSuperHeroById(final Long id) {
        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        heroSuperPowerRepository.deleteAllBySuperheroId(id);
        heroSuperPowerRepository.flush();

        superHeroRepository.deleteById(id);
        superHeroRepository.flush();

    }

    @Override
    @Caching(evict = { @CacheEvict(value = "hero", allEntries = true), @CacheEvict(value = "heroes", allEntries = true),
            @CacheEvict(value = "pagedheroes", allEntries = true), @CacheEvict(value = "allheroes", allEntries = true),
            @CacheEvict(value = "pagedallheroes", allEntries = true),
            @CacheEvict(value = "powers", allEntries = true) })
    public void deleteAllSuperHeros() {
        heroSuperPowerRepository.deleteAll();
        heroSuperPowerRepository.flush();
        superHeroRepository.deleteAll();
        superHeroRepository.flush();

    }

    private SuperHeroEntity getEntityById(final Long id) {
        return superHeroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Not found hero with id %s", id)));
    }

    private SuperHeroDomain validateHeroToCreate(final SuperHeroDTO superHeroDTO){
        this.validateHeroData(superHeroDTO);
        this.checkIfHeroAlreadyExists(superHeroDTO.getName());
        return SuperHeroDomainMapper.INSTANCE.dtoToDomain(superHeroDTO);
    }

    private SuperHeroDomain validateHeroToUpdate(final Long id, final SuperHeroDTO superHeroDTO){
        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        this.validateHeroData(superHeroDTO);
        this.checkIfHeroAlreadyExists(id, superHeroDTO.getName());
        return SuperHeroDomainMapper.INSTANCE.dtoToDomain(superHeroDTO);
    }

    private void validateHeroData(final SuperHeroDTO superHeroDTO) {
        if (superHeroDTO == null) {
            throw new BusinessRuleViolatedException(HERO_MANDATORY);
        }
        if (ParamUtils.paramNotInformed(superHeroDTO.getName())) {
            throw new BusinessRuleViolatedException(NAME_EMPTY);
        }
        if (ParamUtils.paramNotInformed(superHeroDTO.getSuperPower())) {
            throw new BusinessRuleViolatedException(POWERS_EMPTY);
        }
    }

    private void checkIfHeroAlreadyExists(final String heroName) {
        superHeroRepository.findFirstByNameIgnoreCase(heroName)
                .ifPresent(this::throwAlreadyExistException);
    }

    private void checkIfHeroAlreadyExists(final Long id, final String heroName) {
        superHeroRepository.findFirstByNameIgnoreCase(heroName).ifPresent(h -> {
            if (h.getId() != id.longValue()) {
                throwAlreadyExistException(h);
            }
        });
    }

    private void throwAlreadyExistException(final SuperHeroEntity superHeroEntity) {
        throw new AlreadyExistException("This Hero already exists: " + superHeroEntity.toString());
    }



}
