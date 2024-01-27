package com.w2m.heroestest.restapi.services;

import com.w2m.heroestest.model.domain.SuperHeroDomain;
import com.w2m.heroestest.model.dto.SuperHeroDTO;
import com.w2m.heroestest.model.enums.SuperPower;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jruizh
 */
public interface SuperHeroesService {

    List<SuperHeroDomain> getAllSuperHeroes();

    Page<SuperHeroDomain> getAllSuperHeroes(final Pageable pageable);

    List<SuperHeroDomain> getAllSuperHeroesByName(@NotNull final String name);

    Page<SuperHeroDomain> getAllSuperHeroesByName(@NotNull final String name, final Pageable pageable);

    SuperHeroDomain findById(@NotNull final Long id);

    List<SuperHeroDomain> getAllSuperHeroesBySuperPower(@NotNull final SuperPower power);

    SuperHeroDomain createSuperHero(@NotNull final SuperHeroDTO superHeroDTO);

    SuperHeroDomain addSuperPower(@NotNull final Long id, @NotNull final SuperPower power);

    SuperHeroDomain updateSuperHero(@NotNull final Long id, @NotNull final SuperHeroDTO superHeroDTO);

    void deleteSuperHeroById(long id);

    void deleteAllSuperHeros();

}
