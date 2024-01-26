package com.w2m.heroestest.core.model.persistence.repositories;

import com.w2m.heroestest.core.model.persistence.entities.SuperHeroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SuperHeroRepository extends JpaRepository<SuperHeroEntity, Long> {
    Optional<SuperHeroEntity> findById(final Long id);

    List<SuperHeroEntity> findByNameContaining(final String name);

    Page<SuperHeroEntity> findByNameContainingIgnoreCase(final String name, Pageable pageable);

    Optional<SuperHeroEntity> findFirstByNameIgnoreCase(final String name);

}