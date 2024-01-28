package com.w2m.heroestest.factories;

import com.w2m.heroestest.model.domain.SuperHeroDomain;
import com.w2m.heroestest.model.domain.SuperPowerDomain;
import com.w2m.heroestest.model.dto.SuperHeroDTO;
import com.w2m.heroestest.model.dto.SuperPowerDTO;
import com.w2m.heroestest.model.enums.SuperPower;
import com.w2m.heroestest.restapi.persistence.entities.HeroSuperPowerEntity;
import com.w2m.heroestest.restapi.persistence.entities.SuperHeroEntity;
import com.w2m.heroestest.restapi.server.requests.HeroRequest;

import java.util.List;

/**
 * The Class SuperHeroFactory.
 */
public final class SuperHeroFactory {

    public static Long HERO_ID = 100L;

    public static Long POWER_ID = 288L;

    public static String NAME = "SuperLopez";

    public static SuperHeroEntity getEntity() {
        return getEntity(HERO_ID);
    }
    public static SuperHeroEntity getEntity(Long heroId) {
        List<HeroSuperPowerEntity> powers = getPowersEntity(heroId) ;
        SuperHeroEntity mySuperHeroEntity = SuperHeroEntity.builder()
                .id(heroId)
                .name(NAME)
                .description("none")
                .superPower(powers).build();
        return mySuperHeroEntity;
    }
    public static List<HeroSuperPowerEntity> getPowersEntity(Long heroId) {
        return List.of(getPowerEntity(heroId));
    }

    public static HeroSuperPowerEntity getPowerEntity(Long heroId) {
        return getPowerEntity(heroId,POWER_ID);
    }

    public static HeroSuperPowerEntity getPowerEntity(Long heroId,Long powerId) {
        return HeroSuperPowerEntity.builder().id(powerId).superheroId(heroId).superPower(SuperPower.TELEKINESIS).build();
    }
    public static SuperHeroDTO getDTO() {
        return getDTO(HERO_ID);
    }
    public static SuperHeroDTO getDTO(Long heroId) {
        List<SuperPowerDTO> powers = getPowersDTO(heroId) ;
        SuperHeroDTO mySuperHeroDto = SuperHeroDTO.builder()
                .id(heroId)
                .name(NAME)
                .description("none")
                .superPower(powers).build();
        return mySuperHeroDto;
    }

    public static List<SuperPowerDTO> getPowersDTO(Long heroId) {
        return List.of(getPowerDTO(heroId));
    }

    public static SuperPowerDTO getPowerDTO(Long heroId) {
        return getPowerDTO(heroId,POWER_ID);
    }

    public static SuperPowerDTO getPowerDTO(Long heroId,Long powerId) {
        return SuperPowerDTO.builder().id(powerId).superheroId(heroId).superPower(SuperPower.TELEKINESIS).build();
    }

    public static SuperHeroDomain getDO() {
        return getDO(HERO_ID);
    }

    public static SuperHeroDomain getDO(Long heroId) {
        List<SuperPowerDomain> powers = getPowersDO(heroId) ;
        SuperHeroDomain mySuperHeroDomain = SuperHeroDomain.builder()
                .id(heroId)
                .name(NAME)
                .description("none")
                .superPower(powers).build();
        return mySuperHeroDomain;
    }

    public static List<SuperPowerDomain> getPowersDO(Long heroId) {
        return List.of(getPowerDO(heroId));
    }

    public static SuperPowerDomain getPowerDO(Long heroId) {
        return getPowerDO(heroId,POWER_ID);
    }

    public static SuperPowerDomain getPowerDO(Long heroId,Long powerId) {
        return SuperPowerDomain.builder().id(powerId).superheroId(heroId).superPower(SuperPower.TELEKINESIS).build();
    }
    public static HeroRequest getRequest() {
        List<SuperPower> powers = getPowers() ;
        HeroRequest mySuperHeroDomain = HeroRequest.builder()
                .name(NAME)
                .description("none")
                .superPower(powers).build();
        return mySuperHeroDomain;
    }

    public static List<SuperPower> getPowers() {
        return List.of(SuperPower.TELEKINESIS);
    }



}