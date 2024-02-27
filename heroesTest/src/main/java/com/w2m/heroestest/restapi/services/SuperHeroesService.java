package com.w2m.heroestest.restapi.services;

import com.w2m.heroestest.model.domain.SuperHeroDomain;
import com.w2m.heroestest.model.dto.SuperHeroDTO;
import com.w2m.heroestest.model.enums.SuperPower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jruizh
 */
public interface SuperHeroesService {

    List<SuperHeroDomain> getAllSuperHeroes();

    Page<SuperHeroDomain> pageAllSuperHeroes(final Pageable pageable);

    List<SuperHeroDomain> getAllSuperHeroesByName(final String name);

    Page<SuperHeroDomain> pageAllSuperHeroesByName(final String name, final Pageable pageable);

    SuperHeroDomain findById(final Long id);

    List<SuperHeroDomain> getAllSuperHeroesBySuperPower(final SuperPower power);

    SuperHeroDomain createSuperHero(final SuperHeroDTO superHeroDTO);

    SuperHeroDomain addSuperPower(final Long id, final SuperPower power);

    SuperHeroDomain updateSuperHero(final Long id, final SuperHeroDTO superHeroDTO);

    void deleteSuperHeroById(final Long id);

    void deleteAllSuperHeroes();

}
