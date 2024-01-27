package com.w2m.heroestest.core.restapi.persistence.repositories;

import com.w2m.heroestest.core.model.enums.SuperPower;
import com.w2m.heroestest.core.restapi.persistence.entities.HeroSuperPowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroSuperPowerRepository extends JpaRepository<HeroSuperPowerEntity, Long> {

    List<HeroSuperPowerEntity> findAllBySuperheroId(final Long superheroId);

    void deleteAllBySuperheroId(final Long superheroId);

    List<HeroSuperPowerEntity> findBySuperPower(final SuperPower power);

}