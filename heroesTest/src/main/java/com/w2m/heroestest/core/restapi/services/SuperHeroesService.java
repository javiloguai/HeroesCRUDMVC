package com.w2m.heroestest.core.restapi.services;

import com.w2m.heroestest.core.model.domain.SuperHeroDomain;
import com.w2m.heroestest.core.model.dto.SuperHeroDTO;
import com.w2m.heroestest.core.model.enums.SuperPower;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author jruizh
 */
public interface SuperHeroesService {

    List<SuperHeroDomain> getAllSSuperHeroes();

    List<SuperHeroDomain> getAllSSuperHeroesByName(@NotNull final String name);

    SuperHeroDomain findById(@NotNull final Long id);

    List<SuperHeroDomain> getAllSSuperHeroesBySuperPower(@NotNull final SuperPower power);

    SuperHeroDomain createSuperHero(@NotNull final SuperHeroDTO superHeroDTO);

    SuperHeroDomain addSuperPower(@NotNull final Long id, @NotNull final SuperPower power);

    SuperHeroDomain updateSuperHero(@NotNull final Long id, @NotNull final SuperHeroDTO superHeroDTO);

    void deleteSuperHeroById(long id);

    void deleteAllSuperHeros();

}
